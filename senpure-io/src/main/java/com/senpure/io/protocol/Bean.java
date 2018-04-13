package com.senpure.io.protocol;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * Created by 罗中正 on 2018/4/13 0013.
 */
public abstract class Bean {
    public abstract void write(ByteBuf buf);

    public abstract void read(ByteBuf buf);

    public abstract String toString(String indent);

    public abstract int getSerializedSize();


    public static void writeVarint64(ByteBuf buf, long value) {
        while (true) {
            if ((value & ~0x7F) == 0) {
                buf.writeByte((int) value);
                return;
            } else {
                buf.writeByte(((int) value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }

    public static void writeVarint32(ByteBuf buf, int value) {
        while (true) {
            if ((value & ~0x7F) == 0) {
                buf.writeByte(value);
                return;
            } else {
                buf.writeByte((value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }


    public static int readVarint32(ByteBuf buf) {
        byte tmp = buf.readByte();
        if (tmp >= 0) {
            return tmp;
        }
        int result = tmp & 0x7f;
        if ((tmp = buf.readByte()) >= 0) {
            result |= tmp << 7;
        } else {
            result |= (tmp & 0x7f) << 7;
            if ((tmp = buf.readByte()) >= 0) {
                result |= tmp << 14;
            } else {
                result |= (tmp & 0x7f) << 14;
                if ((tmp = buf.readByte()) >= 0) {
                    result |= tmp << 21;
                } else {
                    result |= (tmp & 0x7f) << 21;
                    result |= (tmp = buf.readByte()) << 28;
                    if (tmp < 0) {
                        // Discard upper 32 bits.
                        for (int i = 0; i < 5; i++) {
                            if (buf.readByte() >= 0) {
                                return result;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static long readVarint64(ByteBuf buf) {
        int shift = 0;
        long result = 0;
        while (shift < 64) {
            final byte b = buf.readByte();
            result |= (long) (b & 0x7F) << shift;
            if ((b & 0x80) == 0) {
                return result;
            }
            shift += 7;
        }
        return result;
    }

    public static long readLong(ByteBuf buf) {
        return buf.readLong();
    }

    public static int readInt(ByteBuf buf) {
        return buf.readInt();
    }

    public static void writeLong(ByteBuf buf, long value) {
        buf.writeLong(value);
    }

    public static void writeInt(ByteBuf buf, int value) {
        buf.writeInt(value);
    }


    public static int encodeZigZag32(int n) {
        return n << 1 ^ n >> 31;
    }

    public static long encodeZigZag64(long n) {
        return n << 1 ^ n >> 63;
    }

    public static int decodeZigZag32(int n) {
        return n >>> 1 ^ -(n & 1);
    }

    public static long decodeZigZag64(long n) {
        return n >>> 1 ^ -(n & 1L);
    }


    public static void writeBean(ByteBuf buf, int fieldIndex, Bean value) {
        if (value != null) {
            writeVarint32(buf, makeTag(fieldIndex, 3));
            writeVarint32(buf, value.getSerializedSize());
            value.write(buf);
        }
    }


    public static void writeVarint32(ByteBuf buf, int fieldIndex, int value) {
        //Constant.WIRETYPE_VARINT
        writeVarint32(buf, makeTag(fieldIndex, 0));
        writeVarint32(buf, value);
    }

    public static void writeSInt32(ByteBuf buf, int fieldIndex, int value) {
        //Constant.WIRETYPE_VARINT
        writeVarint32(buf, makeTag(fieldIndex, 0));
        writeVarint32(buf, encodeZigZag32(value));
    }

    public static int readSInt32(ByteBuf buf) {

        return decodeZigZag32(readVarint32(buf));
    }

    public static void writeSFixed32(ByteBuf buf, int fieldIndex, int value) {
        // Constant.WIRETYPE_FIXED32
        writeVarint32(buf, makeTag(fieldIndex, 1));
        buf.writeInt(value);
    }

    public static void writeFloat(ByteBuf buf, int fieldIndex, float value) {
        // Constant.WIRETYPE_FIXED32
        writeVarint32(buf, makeTag(fieldIndex, 1));

        buf.writeFloat(value);
    }

    public static void writeSFixed64(ByteBuf buf, int fieldIndex, long value) {
        // Constant.WIRETYPE_FIXED64
        writeVarint32(buf, makeTag(fieldIndex, 2));
        buf.writeLong(value);
    }

    public static void writeDouble(ByteBuf buf, int fieldIndex, double value) {
        // Constant.WIRETYPE_FIXED64
        writeVarint32(buf, makeTag(fieldIndex, 2));
        buf.writeDouble(value);
    }

    public static void writeString(ByteBuf buf, int fieldIndex, String value) {
        if (value != null) {
            // Constant.WIRETYPE_LENGTH_DELIMITED
            writeVarint32(buf, makeTag(fieldIndex, 3));
            try {
                byte[] bytes = value.getBytes("utf-8");
                writeVarint32(buf, bytes.length);
                buf.writeBytes(bytes);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    public static String readString(ByteBuf buf) {
        int size = readVarint32(buf);
        if (size == 0) {
            return "";
        }
        byte[] bytes = new byte[size];
        buf.readBytes(bytes);
        try {
            return new String(bytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    static int getTagWriteType(int tag) {
        return tag & 7;
    }

    public static int getTagFieldNumber(int tag) {
        return tag >>> 3;
    }

    public static int makeTag(int fieldIndex, int writeType) {
        return fieldIndex << 3 | writeType;
    }

    public static int readTag(ByteBuf buf, int endIndex) {
        if (buf.readerIndex() == endIndex) {
            return 0;
        }
        return readVarint32(buf);
    }

    public void skip(ByteBuf buf, int tag) {
        switch (getTagWriteType(tag)) {
            case 0:
                readVarint64(buf);
                break;
            case 1:
                buf.skipBytes(4);
                break;
            case 2:
                buf.skipBytes(8);
                break;
            case 3:
                buf.skipBytes(readVarint32(buf));
                break;
        }
    }

}
