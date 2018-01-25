package com.senpure.base.init;


import com.senpure.base.PermissionConstant;
import com.senpure.base.annotation.ExtPermission;
import com.senpure.base.annotation.PermissionVerify;
import com.senpure.base.annotation.ResourceVerify;
import com.senpure.base.menu.MenuGenerator;
import com.senpure.base.model.Menu;
import com.senpure.base.model.Permission;
import com.senpure.base.model.PermissionMenu;
import com.senpure.base.model.URIPermission;
import com.senpure.base.service.AuthorizeService;
import com.senpure.base.service.PermissionService;
import com.senpure.base.service.ResourcesVerifyService;
import com.senpure.base.spring.SpringContextRefreshEvent;
import com.senpure.base.spring.VerifyInterceptor;
import com.senpure.base.util.Assert;
import com.senpure.base.util.Pinyin;
import com.senpure.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

@Order(value = 1)
public class PermissionsGenerator extends SpringContextRefreshEvent {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private AuthorizeService authorizeService;
    @Autowired
    private VerifyInterceptor verifyInterceptor;

    private static Map<Integer, Menu> menuMap = new HashMap<>();
    @Autowired
    private ResourcesVerifyService resourcesVerifyService;

    public static void checkAndPutMenu(Menu menu) {
        if (menuMap.containsKey(menu.getId())) {
            Assert.error(" 菜单ID重复:" + menu.getId() + "," + menu.getText() + "," + menuMap.get(menu.getId()).getText());
        }
        menuMap.put(menu.getId(), menu);
    }


    Map<String, Integer> classMap = new HashMap<>();
    Map<String, Integer> pMap = new HashMap<>();

    int classInt = 0;

    public void sort(String name, Permission permission) {


        Integer i = classMap.get(name);
        if (i == null) {
            i = ++classInt;
            classMap.put(name, i);
        }
        Integer p = pMap.get(name);
        if (p == null) {
            p = -1;
        }
        p++;
        pMap.put(name, p);
        int sort = 9000;
        if (permission.getName().contains("_read")) {
            sort = 1000;
        } else if (permission.getName().contains("_create")) {

            sort = 2000;
        } else if (permission.getName().contains("_update")) {
            sort = 3000;
        } else if (permission.getName().contains("_delete")) {
            sort = 4000;
        }

        if (permission.getName().contains("_owner")) {
            sort += 100;
        }
        sort += p;
        Integer s = Integer.valueOf(i + "" + sort);
        permission.setSort(s);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.debug("准备检查代码中的权限，生成数据");
        RequestMappingHandlerMapping rm = event.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
        verifyInterceptor.setHandlerMapping(rm);
        Map<RequestMappingInfo, HandlerMethod> map = rm.getHandlerMethods();
        Iterator<Map.Entry<RequestMappingInfo, HandlerMethod>> iterator = map.entrySet().iterator();
        List<Permission> createPermissions = new ArrayList<>(16);
        Map<String, Permission> permissionMap = new HashMap<>(128);
        Map<String, List<URIPermission>> uriPermissionMap = new HashMap<>(256);
        List<PermissionMenu> permissionMenus = new ArrayList<>(16);
        while (iterator.hasNext()) {
            Map.Entry<RequestMappingInfo, HandlerMethod> entry = iterator.next();
            RequestMappingInfo info = entry.getKey();

            HandlerMethod handlerMethod = entry.getValue();

            PermissionVerify permissionVerify = handlerMethod.getMethod().getAnnotation(PermissionVerify.class);
            String pname = null;
            if (permissionVerify != null) {
                String suffix = mapping2Suffix(info, handlerMethod);
                StringBuilder sname = new StringBuilder();
                Iterator<String> it = info.getPatternsCondition().getPatterns().iterator();
                List<String> uriAndMethod = new ArrayList<>();
                while (true) {
                    String uri = it.next();
                    uriAndMethod.add(uri + "->" + handlerMethod.getMethod().getName());
                    logger.info(info.getMethodsCondition().getMethods().toString());
                    sname.append(uri);
                    if (it.hasNext()) {
                        sname.append("||");
                    } else {
                        break;
                    }
                }

                sname.append("_").append(suffix);
                // logger.debug(sname);
                Permission permission = new Permission();


                permission.setDatabaseUpdate(false);
                if (StringUtil.isExist(permissionVerify.value())) {
                    permission.setReadableName(permissionVerify.value());
                } else {
                    permission.setReadableName(sname.toString());
                }
                String name = permissionVerify.name();
                if (StringUtil.isExist(name)) {
                    permission.setName(name);
                } else {
                    permission.setName(sname.toString());
                }
                pname = permission.getName();
                permission.setType(PermissionConstant.PERMISSION_TYPE_NORMAL);
                if (permission.getName().endsWith("_owner")) {
                    permission.setType(PermissionConstant.PERMISSION_TYPE_OWNER);
                } else {
                    permission.setDescription(sname.toString() + "->" + handlerMethod.getMethod().getName());
                }
                sort(handlerMethod.getBeanType().getName(), permission);
                List<URIPermission> uriPermissions = new ArrayList<>();
                for (String um : uriAndMethod) {
                    URIPermission uriPermission = new URIPermission();
                    //uriPermission.setUriAndMethod(um);
                    uriPermission.setPermissionId(1L);
                    uriPermissions.add(uriPermission);

                }
                // authorizeService.checkPermission(permission, uriPermissions);
                checkPermission(permissionMap, permission, uriPermissionMap, uriPermissions);
                ResourceVerify[] resourceVerifies = handlerMethod.getMethod().getAnnotationsByType(ResourceVerify.class);
                if (resourceVerifies != null && resourceVerifies.length > 0) {
                    for (int i = 0; i < resourceVerifies.length; i++) {
                        if (!resourcesVerifyService.hasVerifyService(resourceVerifies[i].value())) {
                            Assert.error(info.getPatternsCondition() + ",资源验证器[" + resourceVerifies[i].value() + "]没有注册");
                        }
                    }
                    Permission resourcePermission = new Permission();
                    if (permission.getName().endsWith("_owner")) {
                        resourcePermission.setName(permission.getName());
                    } else {
                        resourcePermission.setName(permission.getName() + "_owner");
                    }

                    resourcePermission.setDatabaseUpdate(false);
                    String ownerPname = resourceVerifies[0].permissionName();
                    if (ownerPname.length() > 0) {
                        resourcePermission.setReadableName(ownerPname);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(permission.getReadableName())
                                .insert(2, "我的");
                        resourcePermission.setReadableName(sb.toString());
                    }
                    resourcePermission.setType(PermissionConstant.PERMISSION_TYPE_OWNER);
                    resourcePermission.setDescription(sname.toString() + "_owner" + "->" + handlerMethod.getMethod().getName());
                    List<URIPermission> resourceURIPermissions = new ArrayList<>();
                    for (String um : uriAndMethod) {
                        URIPermission uriPermission = new URIPermission();
                        uriPermission.setPermissionId(2L);
                        //uriPermission.setUriAndMethod(um);
                        if (!permission.getName().endsWith("_owner")) {
                            resourceURIPermissions.add(uriPermission);
                        }

                    }
                    sort(handlerMethod.getBeanType().getName(), resourcePermission);
                    checkPermission(permissionMap, resourcePermission, uriPermissionMap, resourceURIPermissions);
                }

                //owner结尾但是没有配置资源验证注解
                else if (permission.getName().endsWith("_owner")) {
                    permission.setDescription(sname.toString() + "_owner" + "->" + handlerMethod.getMethod().getName());
                }

            }
            //
            //生成菜单
            MenuGenerator menuGenerator = handlerMethod.getMethod().getAnnotation(MenuGenerator.class);
            if (menuGenerator != null) {
                Menu menu = new Menu();
                menu.setDatabaseUpdate(false);
                menu.setDirectView(false);
                setBaseProp(menuGenerator, menu);
                if (menuMap.get(menu.getId()) != null) {
                    Assert.error("菜单ID重复" + menu.getId() + "," + handlerMethod.getBeanType());
                } else {
                    menuMap.put(menu.getId(), menu);
                }
                if (pname != null) {
                    PermissionMenu permissionMenu = new PermissionMenu();
                    permissionMenu.setDataBaseUpdate(false);
                    permissionMenu.setMenuId(menu.getId());
                    permissionMenu.setPermissionName(pname);
                    permissionMenus.add(permissionMenu);
                } else {
                    menu.setDirectView(true);
                }

                if (menuGenerator.uri().length() > 0) {
                    menu.setUri(menuGenerator.uri());
                } else {
                    //取第一个
                    menu.setUri(info.getPatternsCondition().getPatterns().iterator().next());
                }
                if (menuGenerator.parentId() == 0) {
                    MenuGenerator parent = handlerMethod.getBeanType().getAnnotation(MenuGenerator.class);
                    if (parent != null) {
                        menu.setParentId(parent.id());
                        Menu parentMenu = new Menu();
                        parentMenu.setDatabaseUpdate(false);
                        setBaseProp(parent, parentMenu);
                        if (menuMap.get(parentMenu.getId()) == null) {
                            menuMap.put(parentMenu.getId(), parentMenu);
                        }
                    } else {
                        menu.setParentId(0);
                    }
                } else {
                    menu.setParentId(menuGenerator.parentId());
                }
            }
        }

        //extPermission
        extPermission(event, permissionMap, uriPermissionMap);

        createPermissions.addAll(permissionMap.values());
        if (!createPermissions.isEmpty()) {
            authorizeService.syncPermission(permissionMap.values(), uriPermissionMap);
        }
        if (menuMap.size() > 0) {
            authorizeService.syncMenu(menuMap.values());
            authorizeService.syncMenuPermission(permissionMenus);
        }
        authorizeService.loadStatic();


    }

    //将同一个permisson name 合并
    private Permission checkPermission(Map<String, Permission> permissionMap, Permission permission,
                                       Map<String, List<URIPermission>> uriPermissionMap, List<URIPermission> uriPermissions) {
        Permission beforePermission = permissionMap.get(permission.getName());
        if (beforePermission != null) {
            List<URIPermission> beforeURIPermission = uriPermissionMap.get(permission.getName());
            beforeURIPermission.addAll(uriPermissions);
            if (beforePermission.getDescription() == null) {
                beforePermission.setDescription(permission.getDescription());
            } else {
                if (permission.getDescription() != null) {
                    beforePermission.setDescription(beforePermission.getDescription() + "||" + permission.getDescription());
                }

            }
            return beforePermission;
        } else {
            permissionMap.put(permission.getName(), permission);
            uriPermissionMap.put(permission.getName(), uriPermissions);
            return permission;
        }
    }

    public static StringBuilder mapping2permissionName(RequestMappingInfo info, HandlerMethod handlerMethod) {
        Iterator<String> it = info.getPatternsCondition().getPatterns().iterator();
        StringBuilder sname = new StringBuilder();

        List<String> uriAndMethod = new ArrayList<>();
        while (true) {
            String uri = it.next();
            sname.append(uri);
            uriAndMethod.add(uri + "->" + handlerMethod.getMethod().getName());
            if (it.hasNext()) {
                sname.append("||");
            } else {
                break;
            }
        }
        return sname;
    }

    private static String mapping2Suffix(RequestMappingInfo info, HandlerMethod handlerMethod) {

        String suffix = "read";
        if (info.getMethodsCondition().isEmpty()) {
            suffix = method2Suffix(handlerMethod, false);
        } else {
            List<String> methods = new ArrayList<>();
            info.getMethodsCondition().getMethods().forEach(requestMethod ->
                    methods.add(requestMethod.toString().toUpperCase()));
            if (methods.contains("DELETE")) {
                suffix = "delete";
            } else if (
                    methods.contains("PUT")) {
                suffix = "create";
            } else if (
                    methods.contains("POST")) {
                if (methods.contains("GET")) {
                    suffix = method2Suffix(handlerMethod, false);
                } else {
                    suffix = method2Suffix(handlerMethod, true);
                }

            }

        }
        return suffix;
    }

    public static String method2Suffix(HandlerMethod handlerMethod, boolean post) {
        String suffix = "create";
        if (handlerMethod.getMethod().getName().startsWith("add") || handlerMethod.getMethod().getName().startsWith("create")) {
            suffix = "create";
        } else if (handlerMethod.getMethod().getName().startsWith("del")) {
            suffix = "delete";
        } else if (handlerMethod.getMethod().getName().startsWith("update") || handlerMethod.getMethod().getName().startsWith("change")) {
            suffix = "update";
        } else {
            suffix = post ? "update" : "read";
        }
        return suffix;
    }


    private void setBaseProp(MenuGenerator menuGenerator, Menu menu) {
        menu.setText(menuGenerator.text());
        if (menuGenerator.description().length() > 0) {
            menu.setDescription(menuGenerator.description());
        }
        if (menuGenerator.config().length() > 0) {
            menu.setConfig(menuGenerator.config());
        }

        if (menuGenerator.i18nKey().length() > 0) {
            menu.setI18nKey(menuGenerator.i18nKey());
        } else {
            menu.setI18nKey(Pinyin.toAccount(menu.getText())[0]);
            menu.setI18nKey(Pinyin.toAccount(menu.getText())[0].replace(" ", ".").toLowerCase());
        }
        menu.setIcon(menuGenerator.icon());
        menu.setId(menuGenerator.id());
        menu.setSort(menuGenerator.sort());

    }


    private void extPermission(ContextRefreshedEvent event, Map<String, Permission> permissionMap,
                               Map<String, List<URIPermission>> uriPermissionMap) {
        ApplicationContext context = event.getApplicationContext();

        String beans[] = context.getBeanNamesForAnnotation(ExtPermission.class);
        for (String bean : beans) {

            Object obj = context.getBean(bean);
            Method[] methods = obj.getClass().getDeclaredMethods();

            for (Method method : methods) {
                ExtPermission extPermission = method.getAnnotation(ExtPermission.class);
                if (extPermission != null) {
                    Permission permission = new Permission();

                    permission.setDatabaseUpdate(false);
                   // permission.setName(extPermission.uri());
                    permission.setReadableName(permission.getName());
                    permission.setType(PermissionConstant.PERMISSION_TYPE_NORMAL);
                    if (permission.getName().endsWith("_owner")) {
                        permission.setType(PermissionConstant.PERMISSION_TYPE_OWNER);
                    }
                    String methodStr = "";

                   // permission.setDescription(extPermission.uri() + "->" + methodStr);

                    sort(obj.getClass().getName(), permission);
                    List<URIPermission> uriPermissionList = new ArrayList<>();
                    URIPermission uriPermission = new URIPermission();
                    uriPermission.setPermissionId(0L);
                   // uriPermission.setUriAndMethod(extPermission.uri() + "->" + methodStr);
                    uriPermissionList.add(uriPermission);
                    checkPermission(permissionMap, permission, uriPermissionMap, uriPermissionList);
                }
            }
        }
    }

    public static void main(String[] args) {
        String str = "创建容器";
        StringBuilder sb = new StringBuilder(str);
        sb.insert(2, "我的");
        System.out.println(sb);

    }
}
