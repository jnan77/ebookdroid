package org.ebookdroid.fb2droid.codec;

import java.util.HashMap;
import java.util.Map;

public class FB2Words {

    public static int words = 0;
    public static int uniques = 0;

    static final FB2Word key = new FB2Word();

    final Map<FB2Word, FB2TextElement> all = new HashMap<FB2Word, FB2TextElement>(32 * 10241);

    public FB2TextElement get(final char[] ch, final int st, final int len, final RenderingStyle style) {
        words++;

        key.reuse(ch, st, len);

        FB2TextElement e = all.get(key);
        if (e == null) {
            e = new FB2TextElement(ch, st, len, style);
            all.put(new FB2Word(key), e);
            uniques++;
        }
        return e;
    }

    static class FB2Word {

        int hash;
        char[] chars;
        int start;
        int length;

        public FB2Word() {
        }

        public FB2Word(final FB2Word w) {
            this.chars = w.chars;
            this.hash = w.hash;
            this.length = w.length;
            this.start = w.start;
        }

        public void reuse(final char[] ch, final int st, final int len) {
            this.chars = ch;
            this.start = st;
            this.length = len;

            int h = 0;
            int off = st;
            for (int i = 0; i < len; i++) {
                h = 31 * h + ch[off++];
            }
            hash = h;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof FB2Word) {
                final FB2Word that = (FB2Word) obj;
                if (this.hash != that.hash) {
                    return false;
                }
                if (this.length != that.length) {
                    return false;
                }
                for (int i = 0; i < this.length; i++) {
                    final char c1 = this.chars[i + this.start];
                    final char c2 = that.chars[i + that.start];
                    if (c1 != c2) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }
}
