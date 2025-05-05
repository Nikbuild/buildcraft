/*     */ package buildcraft.core.blueprints;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BptDataStream
/*     */   implements DataInput, DataOutput
/*     */ {
/*     */   public boolean isFirst = true;
/*     */   private Writer writer;
/*     */   private Reader reader;
/*     */   
/*     */   public BptDataStream(Writer writer) {
/*  24 */     this.writer = writer;
/*     */   }
/*     */   
/*     */   public BptDataStream(Reader reader) {
/*  28 */     this.reader = reader;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(byte[] b) throws IOException {
/*  33 */     char[] c = new char[b.length];
/*     */     
/*  35 */     this.reader.read(c);
/*     */     
/*  37 */     for (int i = 0; i < b.length; i++) {
/*  38 */       b[i] = (byte)c[i];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFully(byte[] b, int off, int len) throws IOException {
/*  44 */     char[] c = new char[len];
/*     */     
/*  46 */     this.reader.read(c);
/*     */     
/*  48 */     for (int i = 0; i < len; i++) {
/*  49 */       b[off + i] = (byte)c[i];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int skipBytes(int n) throws IOException {
/*  55 */     return this.reader.read(new char[n]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean() throws IOException {
/*  60 */     String s = readUTF();
/*     */     
/*  62 */     return "T".equals(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() throws IOException {
/*  67 */     return (byte)(int)readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedByte() throws IOException {
/*  72 */     return (short)(int)readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() throws IOException {
/*  77 */     return (short)(int)readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() throws IOException {
/*  82 */     return (int)readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public char readChar() throws IOException {
/*  87 */     return readUTF().charAt(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() throws IOException {
/*  92 */     return (int)readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() throws IOException {
/*  97 */     StringBuilder builder = new StringBuilder();
/*     */     
/*  99 */     boolean exit = false;
/*     */     
/* 101 */     while (!exit) {
/* 102 */       int i = this.reader.read();
/* 103 */       if (i < 0) {
/*     */         break;
/*     */       }
/*     */       
/* 107 */       char c = (char)i;
/*     */       
/* 109 */       switch (c) {
/*     */         case ',':
/* 111 */           exit = true;
/*     */           continue;
/*     */       } 
/* 114 */       builder.append(c);
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return Long.parseLong(builder.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() throws IOException {
/* 123 */     return (float)readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() throws IOException {
/* 128 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 130 */     boolean exit = false;
/*     */     
/* 132 */     while (!exit) {
/* 133 */       int i = this.reader.read();
/* 134 */       if (i < 0) {
/*     */         break;
/*     */       }
/*     */       
/* 138 */       char c = (char)i;
/*     */       
/* 140 */       switch (c) {
/*     */         case ',':
/* 142 */           exit = true;
/*     */           continue;
/*     */       } 
/* 145 */       builder.append(c);
/*     */     } 
/*     */ 
/*     */     
/* 149 */     return Double.parseDouble(builder.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public String readLine() throws IOException {
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String readUTF() throws IOException {
/* 159 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 161 */     boolean exit = false;
/*     */     
/* 163 */     char c = (char)this.reader.read();
/*     */     
/* 165 */     if (c != '"') {
/* 166 */       throw new IOException("String does not start with '\"' character");
/*     */     }
/*     */     
/* 169 */     while (this.reader.ready() && !exit) {
/* 170 */       c = (char)this.reader.read();
/*     */       
/* 172 */       switch (c) {
/*     */         case '\\':
/* 174 */           c = (char)this.reader.read();
/*     */           
/* 176 */           switch (c) {
/*     */             case 'n':
/* 178 */               builder.append('\n');
/*     */               continue;
/*     */             case 'r':
/* 181 */               builder.append('\r');
/*     */               continue;
/*     */             case '\\':
/* 184 */               builder.append('\\');
/*     */               continue;
/*     */             case '"':
/* 187 */               builder.append('"');
/*     */               continue;
/*     */           } 
/*     */           
/*     */           continue;
/*     */         case '"':
/* 193 */           exit = true;
/*     */           continue;
/*     */       } 
/* 196 */       builder.append(c);
/*     */     } 
/*     */ 
/*     */     
/* 200 */     int i = this.reader.read();
/* 201 */     c = (char)i;
/*     */     
/* 203 */     if (c != ',' && i > 0) {
/* 204 */       throw new IOException("Missing ',' at end of attribute");
/*     */     }
/* 206 */     return builder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBoolean(boolean v) throws IOException {
/* 227 */     if (v) {
/* 228 */       writeUTF("T");
/*     */     } else {
/* 230 */       writeUTF("F");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeByte(int v) throws IOException {
/* 236 */     writeLong(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeShort(int v) throws IOException {
/* 241 */     writeLong(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChar(int v) throws IOException {
/* 246 */     writeUTF((char)v + "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInt(int v) throws IOException {
/* 252 */     writeLong(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLong(long v) throws IOException {
/* 257 */     handleWriteComma();
/*     */     
/* 259 */     this.writer.append(Long.toString(v));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFloat(float v) throws IOException {
/* 264 */     writeDouble(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDouble(double v) throws IOException {
/* 269 */     handleWriteComma();
/*     */     
/* 271 */     this.writer.append(Double.toString(v));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBytes(String s) throws IOException {
/* 276 */     writeUTF(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChars(String s) throws IOException {
/* 281 */     writeUTF(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeUTF(String s) throws IOException {
/* 286 */     handleWriteComma();
/*     */     
/* 288 */     this.writer.write("\"");
/*     */     
/* 290 */     for (char c : s.toCharArray()) {
/* 291 */       switch (c) {
/*     */         case '\n':
/* 293 */           this.writer.write("\\n");
/*     */           break;
/*     */         case '\r':
/* 296 */           this.writer.write("\\r");
/*     */           break;
/*     */         case '"':
/* 299 */           this.writer.write("\\\"");
/*     */           break;
/*     */         case '\\':
/* 302 */           this.writer.write("\\\\");
/*     */           break;
/*     */         default:
/* 305 */           this.writer.write(c);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 310 */     this.writer.write("\"");
/*     */   }
/*     */   
/*     */   private void handleWriteComma() throws IOException {
/* 314 */     if (!this.isFirst) {
/* 315 */       this.writer.append(",");
/*     */     }
/*     */     
/* 318 */     this.isFirst = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BptDataStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */