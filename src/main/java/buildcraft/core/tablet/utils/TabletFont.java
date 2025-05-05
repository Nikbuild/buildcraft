/*     */ package buildcraft.core.tablet.utils;
/*     */ import buildcraft.api.tablet.TabletBitmap;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class TabletFont {
/*     */   private boolean isBold;
/*     */   private boolean isItalic;
/*     */   private int pointSize;
/*     */   private int maxW;
/*     */   private int maxH;
/*     */   private int ascent;
/*     */   private int descent;
/*     */   
/*     */   public final class Glyph {
/*     */     public byte[] glyphData;
/*     */     private final int loadOffset;
/*     */     private int width;
/*     */     
/*     */     public Glyph(int offset) {
/*  21 */       this.loadOffset = offset;
/*     */     }
/*     */     private int height; private int xOffset; private int yOffset; private int deviceWidth;
/*     */     public void load(byte[] data, int offsetBase) throws Exception {
/*  25 */       ByteArrayInputStream stream = new ByteArrayInputStream(data);
/*  26 */       stream.skip((this.loadOffset - offsetBase));
/*  27 */       this.width = TabletFont.readUnsignedShort(stream);
/*  28 */       this.height = TabletFont.readUnsignedShort(stream);
/*  29 */       this.xOffset = TabletFont.readShort(stream);
/*  30 */       this.yOffset = TabletFont.readShort(stream);
/*  31 */       this.deviceWidth = TabletFont.readShort(stream);
/*     */       
/*  33 */       this.glyphData = new byte[(this.width * this.height + 7) / 8];
/*  34 */       stream.read(this.glyphData);
/*     */     }
/*     */     
/*     */     public int getWidth() {
/*  38 */       return this.width;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/*  42 */       return this.height;
/*     */     }
/*     */     
/*     */     public int getDeviceWidth() {
/*  46 */       return this.deviceWidth;
/*     */     }
/*     */     
/*     */     public int getXOffset() {
/*  50 */       return this.xOffset;
/*     */     }
/*     */     
/*     */     public int getYOffset() {
/*  54 */       return this.yOffset;
/*     */     }
/*     */ 
/*     */     
/*     */     public int draw(TabletBitmap bitmap, int x, int y, int intensity) {
/*  59 */       int yTop = y - this.yOffset * 2 - this.height;
/*  60 */       for (int j = 0; j < this.height; j++) {
/*  61 */         for (int i = 0; i < this.width; i++) {
/*  62 */           int bPos = i + j * this.width >> 3;
/*  63 */           int bMask = 128 >> (i + j * this.width & 0x7);
/*  64 */           if ((this.glyphData[bPos] & bMask) != 0) {
/*  65 */             bitmap.set(x + this.xOffset + i, yTop + j, intensity);
/*     */           }
/*     */         } 
/*     */       } 
/*  69 */       return this.deviceWidth;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private TIntObjectMap<Glyph> glyphs = (TIntObjectMap<Glyph>)new TIntObjectHashMap();
/*     */   
/*     */   public TabletFont(File file) throws Exception {
/*  79 */     this(new FileInputStream(file));
/*     */   }
/*     */   
/*     */   public TabletFont(InputStream stream) throws Exception {
/*  83 */     int loaded = 0;
/*     */     
/*  85 */     while (stream.available() > 0) {
/*  86 */       String section = readString(stream, 4);
/*  87 */       int sectionLength = readInt(stream);
/*  88 */       loaded += 8;
/*     */       
/*  90 */       if ("FAMI".equals(section)) {
/*  91 */         readString(stream, sectionLength);
/*  92 */       } else if ("WEIG".equals(section)) {
/*  93 */         this.isBold = "bold".equals(readString(stream, sectionLength));
/*  94 */       } else if ("SLAN".equals(section)) {
/*  95 */         this.isItalic = "italic".equals(readString(stream, sectionLength));
/*  96 */       } else if ("PTSZ".equals(section)) {
/*  97 */         this.pointSize = readUnsignedShort(stream);
/*  98 */       } else if ("MAXW".equals(section)) {
/*  99 */         this.maxW = readUnsignedShort(stream);
/* 100 */       } else if ("MAXH".equals(section)) {
/* 101 */         this.maxH = readUnsignedShort(stream);
/* 102 */       } else if ("ASCE".equals(section)) {
/* 103 */         this.ascent = readUnsignedShort(stream);
/* 104 */       } else if ("DESC".equals(section)) {
/* 105 */         this.descent = readUnsignedShort(stream);
/* 106 */       } else if ("CHIX".equals(section)) {
/* 107 */         for (int i = 0; i < sectionLength; i += 9) {
/* 108 */           int codePoint = readInt(stream);
/* 109 */           stream.skip(1L);
/* 110 */           int offset = readInt(stream);
/* 111 */           this.glyphs.put(codePoint, new Glyph(offset));
/*     */         } 
/* 113 */       } else if ("DATA".equals(section)) {
/* 114 */         byte[] data = new byte[stream.available()];
/* 115 */         stream.read(data);
/*     */         
/* 117 */         for (Glyph g : this.glyphs.valueCollection()) {
/* 118 */           g.load(data, loaded);
/*     */         }
/*     */       } else {
/* 121 */         stream.skip(sectionLength);
/*     */       } 
/* 123 */       loaded += sectionLength;
/*     */     } 
/*     */     
/* 126 */     stream.close();
/*     */   }
/*     */   
/*     */   public boolean isBold() {
/* 130 */     return this.isBold;
/*     */   }
/*     */   
/*     */   public boolean isItalic() {
/* 134 */     return this.isItalic;
/*     */   }
/*     */   
/*     */   public int getPointSize() {
/* 138 */     return this.pointSize;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 142 */     return this.maxW;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 146 */     return this.maxH;
/*     */   }
/*     */   
/*     */   public int getAscent() {
/* 150 */     return this.ascent;
/*     */   }
/*     */   
/*     */   public int getDescent() {
/* 154 */     return this.descent;
/*     */   }
/*     */   
/*     */   public Glyph getGlyph(int codePoint) {
/* 158 */     return (Glyph)this.glyphs.get(codePoint);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStringWidth(String s) {
/* 163 */     int width = 0;
/* 164 */     for (int i = 0; i < s.length(); i++) {
/* 165 */       width += getGlyph(s.codePointAt(i)).getDeviceWidth();
/*     */     }
/* 167 */     return width;
/*     */   }
/*     */   
/*     */   public int draw(TabletBitmap target, String s, int x, int y, int intensity) {
/* 171 */     int width = 0;
/* 172 */     for (int i = 0; i < s.length(); i++) {
/* 173 */       width += getGlyph(s.codePointAt(i)).draw(target, x + width, y + this.ascent, intensity);
/*     */     }
/* 175 */     return width;
/*     */   }
/*     */   
/*     */   private static int readUnsignedShort(InputStream stream) {
/*     */     try {
/* 180 */       int hi = stream.read();
/* 181 */       return hi << 8 | stream.read();
/* 182 */     } catch (IOException e) {
/* 183 */       e.printStackTrace();
/* 184 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int readShort(InputStream stream) {
/* 189 */     int t = readUnsignedShort(stream);
/* 190 */     if (t >= 32768) {
/* 191 */       return 0 - (t ^ 0xFFFF);
/*     */     }
/* 193 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int readInt(InputStream stream) {
/*     */     try {
/* 199 */       int i = stream.read();
/* 200 */       i = i << 8 | stream.read();
/* 201 */       i = i << 8 | stream.read();
/* 202 */       i = i << 8 | stream.read();
/* 203 */       return i;
/* 204 */     } catch (IOException e) {
/* 205 */       e.printStackTrace();
/* 206 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String readString(InputStream stream, int length) {
/* 211 */     byte[] data = new byte[length];
/*     */     try {
/* 213 */       stream.read(data);
/* 214 */       return new String(data, "ASCII");
/* 215 */     } catch (IOException e) {
/* 216 */       e.printStackTrace();
/* 217 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 222 */     TabletBitmap bitmap = new TabletBitmap(244, 306);
/*     */     try {
/* 224 */       TabletFont font = new TabletFont(TabletFont.class.getClassLoader().getResourceAsStream("assets/buildcraftcore/tablet/test.pf2"));
/* 225 */       font.draw(bitmap, "Hello World!", 1, 1, 4);
/* 226 */     } catch (Exception e) {
/* 227 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\table\\utils\TabletFont.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */