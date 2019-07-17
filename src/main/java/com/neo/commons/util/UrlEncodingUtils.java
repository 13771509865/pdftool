package com.neo.commons.util;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.BitSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.neo.commons.cons.constants.SysConstant;

public class UrlEncodingUtils {
    public static void main(String[] args) {
        String url2 = "http://58.215.166.234/example/doc/doctest1 - 副本.docx";
        String url3 = "http://saasfile.swartz.cn/SaaS2016110821472634286?Expires=1478765624&OSSAccessKeyId=vmAVXWDAixQIiape&Signature=ak0hLXUM7xvHp28OfJeKjJ%2FAL48%3D";
        String url4 = "http://nearbucket.oss-cn-shanghai.aliyuncs.com/%E6%8A%A5%E5%91%8A.pdf?Expires=1480230715&OSSAccessKeyId=TMP.AQFjclHEqVweMQb2uRKqpYbSoVfGL71s5eVoXxVtnaSxeIT16B4dwSDntv-fADAtAhRO7z7S5oENSMxk1wPwonpOAzYYEwIVANM69bp5TGjgZSCMrBd7otpjYuJB&Signature=OmjsFez6b71ZzPy2huubxyf6ZZM%3D";
        String url5 = "http://61.139.94.30:2016/UploadFile/GongWenFile/20161124/24140602596_%E4%BC%9A%E8%AE%AE%E7%BA%AA%E8%A6%81[2016]95%E5%8F%B7.doc";
        String url6 = "http://175.173.244.157:8066/WebService.asmx/../../files/ERD/CustomerFolder/c8982f61-5c4d-4d23-a7ba-6a0fbdae579b.pdf";
        String url7 = "http://a-a_a?:@&=+$,-_.!~*'().com/abc?{111=222}&{333=444}";
        String url8 = "http://media.molyfun.com/mf/depart/file/file_20180309-134307850.pptx?sign=f84f1baf0efc2b5470707d97f0991e62&t=9644ee30";
    }

    // 处理下面的url
    // http://175.173.244.157:8066/WebService.asmx/../../files/ERD/CustomerFolder/c8982f61-5c4d-4d23-a7ba-6a0fbdae579b.pdf";
    public static String encodeUrl(String url) {
        return myEncodingUrl(folderUrl(url));
    }

    private static String folderUrl(String url) {
        // 拿到前面的 协议：IP（域名）[端口]/
        Pattern p = Pattern.compile("^(http|https)://[^/]+/", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(url);
        if (m.find()) {
            String hreadStr = m.group();
            url = url.replace(hreadStr, "");
            // 调整结构
            url = url.replaceAll("[^/]+/\\.\\./", "");
            while (url.startsWith("../")) {
                url = url.substring(3);
            }
            return hreadStr + url;
        }
        return url;
    }

    private static String myEncodingUrl(String url) {
        Pattern p = Pattern.compile("^(http|https)://[^/]+/", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(url);
        if (m.find()) {
            StringBuffer re = new StringBuffer();
            int isSplit = url.indexOf("?");
            String urlPart1 = null;
            String urlPart2 = null;
            if (isSplit > -1) {
                urlPart1 = url.substring(0, isSplit);
                urlPart2 = url.substring(isSplit);
            } else {
                urlPart1 = url;
            }
            String result = MyUrlEncode.DEFAULT.encode(urlPart1, Charset.forName(SysConstant.CHARSET));
            if (urlPart2 != null && !"".equals(urlPart2)) {
                //对{和}进行一下处理，免得报非法字符
                urlPart2 = urlPart2.replaceAll("\\{", "%7b");
                urlPart2 = urlPart2.replaceAll("\\}", "%7d");
                result += urlPart2;
            }
            return result;
        } else {
            return url;
        }
    }

    /**
     * @author zhoufeng
     * @description 抄的tomcat的urlencode
     * @date 2019/6/13
     */
    private static class MyUrlEncode implements Cloneable {
        private static final char[] hexadecimal = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        public static final MyUrlEncode DEFAULT = new MyUrlEncode();
        public static final MyUrlEncode QUERY = new MyUrlEncode();
        private final BitSet safeCharacters;
        private boolean encodeSpaceAsPlus;

        public MyUrlEncode() {
            this(new BitSet(256));

            char i;
            for (i = 'a'; i <= 'z'; ++i) {
                this.addSafeCharacter(i);
            }

            for (i = 'A'; i <= 'Z'; ++i) {
                this.addSafeCharacter(i);
            }

            for (i = '0'; i <= '9'; ++i) {
                this.addSafeCharacter(i);
            }

        }

        private MyUrlEncode(BitSet safeCharacters) {
            this.encodeSpaceAsPlus = false;
            this.safeCharacters = safeCharacters;
        }

        public void addSafeCharacter(char c) {
            this.safeCharacters.set(c);
        }

        public void removeSafeCharacter(char c) {
            this.safeCharacters.clear(c);
        }

        public void setEncodeSpaceAsPlus(boolean encodeSpaceAsPlus) {
            this.encodeSpaceAsPlus = encodeSpaceAsPlus;
        }

        public String encode(String path, Charset charset) {
            int maxBytesPerChar = 10;
            StringBuilder rewrittenPath = new StringBuilder(path.length());
            ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);
            OutputStreamWriter writer = new OutputStreamWriter(buf, charset);

            for (int i = 0; i < path.length(); ++i) {
                int c = path.charAt(i);
                if (this.safeCharacters.get(c)) {
                    rewrittenPath.append((char) c);
                } else if (this.encodeSpaceAsPlus && c == ' ') {
                    rewrittenPath.append('+');
                } else {
                    try {
                        writer.write((char) c);
                        writer.flush();
                    } catch (IOException var14) {
                        buf.reset();
                        continue;
                    }

                    byte[] ba = buf.toByteArray();

                    for (int j = 0; j < ba.length; ++j) {
                        byte toEncode = ba[j];
                        rewrittenPath.append('%');
                        int low = toEncode & 15;
                        int high = (toEncode & 240) >> 4;
                        rewrittenPath.append(hexadecimal[high]);
                        rewrittenPath.append(hexadecimal[low]);
                    }

                    buf.reset();
                }
            }

            return rewrittenPath.toString();
        }

        public Object clone() {
            MyUrlEncode result = new MyUrlEncode((BitSet) this.safeCharacters.clone());
            result.setEncodeSpaceAsPlus(this.encodeSpaceAsPlus);
            return result;
        }

        static {
            DEFAULT.addSafeCharacter('-');
            DEFAULT.addSafeCharacter('.');
            DEFAULT.addSafeCharacter('_');
            DEFAULT.addSafeCharacter('~');
            DEFAULT.addSafeCharacter('!');
            DEFAULT.addSafeCharacter('$');
            DEFAULT.addSafeCharacter('&');
            DEFAULT.addSafeCharacter('\'');
            DEFAULT.addSafeCharacter('(');
            DEFAULT.addSafeCharacter(')');
            DEFAULT.addSafeCharacter('*');
            DEFAULT.addSafeCharacter('+');
            DEFAULT.addSafeCharacter(',');
            DEFAULT.addSafeCharacter(';');
            DEFAULT.addSafeCharacter('=');
            DEFAULT.addSafeCharacter(':');
            DEFAULT.addSafeCharacter('@');
            DEFAULT.addSafeCharacter('/');
            QUERY.setEncodeSpaceAsPlus(true);
            QUERY.addSafeCharacter('*');
            QUERY.addSafeCharacter('-');
            QUERY.addSafeCharacter('.');
            QUERY.addSafeCharacter('_');
            QUERY.addSafeCharacter('=');
            QUERY.addSafeCharacter('&');
        }
    }
}