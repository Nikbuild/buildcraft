/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.api.library.LibraryAPI;
/*     */ import buildcraft.core.blueprints.LibraryId;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LibraryDatabase
/*     */ {
/*     */   protected Set<LibraryId> blueprintIds;
/*  33 */   protected LibraryId[] pages = new LibraryId[0];
/*     */ 
/*     */   
/*     */   private File outputDir;
/*     */ 
/*     */   
/*     */   private List<File> inputDirs;
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(String[] inputPaths, String outputPath) {
/*  44 */     this.outputDir = new File(outputPath);
/*     */     
/*  46 */     if (!this.outputDir.exists()) {
/*  47 */       this.outputDir.mkdirs();
/*     */     }
/*     */     
/*  50 */     this.inputDirs = new ArrayList<File>();
/*     */     
/*  52 */     for (int i = 0; i < inputPaths.length; i++) {
/*  53 */       File inputDir = new File(inputPaths[i]);
/*  54 */       if (inputDir.exists()) {
/*  55 */         this.inputDirs.add(inputDir);
/*     */       }
/*     */     } 
/*     */     
/*  59 */     refresh();
/*     */   }
/*     */   
/*     */   public void refresh() {
/*  63 */     this.blueprintIds = new TreeSet<LibraryId>();
/*  64 */     for (File f : this.inputDirs) {
/*  65 */       loadIndex(f);
/*     */     }
/*     */   }
/*     */   
/*     */   public void deleteBlueprint(LibraryId id) {
/*  70 */     File blueprintFile = getBlueprintFile(id);
/*     */     
/*  72 */     if (blueprintFile != null) {
/*  73 */       blueprintFile.delete();
/*  74 */       this.blueprintIds.remove(id);
/*  75 */       this.pages = new LibraryId[this.blueprintIds.size()];
/*  76 */       this.pages = this.blueprintIds.<LibraryId>toArray(this.pages);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected File getBlueprintFile(LibraryId id) {
/*  81 */     String name = String.format(Locale.ENGLISH, "%s." + id.extension, new Object[] { id.toString() });
/*     */     
/*  83 */     for (File dir : this.inputDirs) {
/*  84 */       File f = new File(dir, name);
/*     */       
/*  86 */       if (f.exists()) {
/*  87 */         return f;
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   protected File getBlueprintOutputFile(LibraryId id) {
/*  95 */     String name = String.format(Locale.ENGLISH, "%s." + id.extension, new Object[] { id.toString() });
/*     */     
/*  97 */     return new File(this.outputDir, name);
/*     */   }
/*     */   
/*     */   public void add(LibraryId base, NBTTagCompound compound) {
/* 101 */     save(base, compound);
/*     */     
/* 103 */     if (!this.blueprintIds.contains(base)) {
/* 104 */       this.blueprintIds.add(base);
/* 105 */       this.pages = this.blueprintIds.<LibraryId>toArray(this.pages);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void save(LibraryId base, NBTTagCompound compound) {
/* 110 */     byte[] data = NBTUtils.save(compound);
/* 111 */     base.generateUniqueId(data);
/* 112 */     File blueprintFile = getBlueprintOutputFile(base);
/*     */     
/* 114 */     if (!blueprintFile.exists()) {
/*     */       try {
/* 116 */         FileOutputStream f = new FileOutputStream(blueprintFile);
/* 117 */         f.write(data);
/* 118 */         f.close();
/* 119 */       } catch (IOException ex) {
/* 120 */         BCLog.logger.error(String.format("Failed to save library file: %s %s", new Object[] { blueprintFile.getName(), ex.getMessage() }));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadIndex(File directory) {
/* 126 */     FilenameFilter filter = new FilenameFilter()
/*     */       {
/*     */         public boolean accept(File dir, String name) {
/* 129 */           int dotIndex = name.lastIndexOf('.') + 1;
/* 130 */           String extension = name.substring(dotIndex);
/* 131 */           return (LibraryAPI.getHandlerFor(extension) != null);
/*     */         }
/*     */       };
/*     */     
/* 135 */     if (directory.exists()) {
/* 136 */       File[] files = directory.listFiles(filter);
/* 137 */       if (files == null || files.length == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 141 */       for (File blueprintFile : files) {
/* 142 */         String fileName = blueprintFile.getName();
/*     */         
/* 144 */         LibraryId id = new LibraryId();
/*     */         
/* 146 */         int sepIndex = fileName.lastIndexOf('-');
/* 147 */         int dotIndex = fileName.lastIndexOf('.');
/*     */         
/* 149 */         if (dotIndex > 0) {
/* 150 */           String extension = fileName.substring(dotIndex + 1);
/*     */           
/* 152 */           if (sepIndex > 0) {
/* 153 */             String prefix = fileName.substring(0, sepIndex);
/* 154 */             String suffix = fileName.substring(sepIndex + 1);
/*     */             
/* 156 */             id.name = prefix;
/* 157 */             id.uniqueId = LibraryId.toBytes(suffix.substring(0, suffix.length() - extension.length() + 1));
/*     */           } else {
/* 159 */             id.name = fileName.substring(0, dotIndex);
/* 160 */             id.uniqueId = new byte[0];
/*     */           } 
/* 162 */           id.extension = extension;
/*     */           
/* 164 */           if (!this.blueprintIds.contains(id)) {
/* 165 */             this.blueprintIds.add(id);
/*     */           }
/*     */         } else {
/* 168 */           BCLog.logger.warn("Found incorrectly named (no extension) blueprint file: '%s'!", new Object[] { fileName });
/*     */         } 
/*     */       } 
/*     */       
/* 172 */       this.pages = this.blueprintIds.<LibraryId>toArray(new LibraryId[this.blueprintIds.size()]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean exists(LibraryId id) {
/* 177 */     return this.blueprintIds.contains(id);
/*     */   }
/*     */   
/*     */   public NBTTagCompound load(LibraryId id) {
/* 181 */     if (id == null) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     NBTTagCompound compound = load(getBlueprintFile(id));
/* 186 */     return compound;
/*     */   }
/*     */   
/*     */   public static NBTTagCompound load(File blueprintFile) {
/* 190 */     if (blueprintFile != null && blueprintFile.exists()) {
/*     */       try {
/* 192 */         FileInputStream f = new FileInputStream(blueprintFile);
/* 193 */         byte[] data = new byte[(int)blueprintFile.length()];
/* 194 */         f.read(data);
/* 195 */         f.close();
/*     */         
/* 197 */         return NBTUtils.load(data);
/* 198 */       } catch (FileNotFoundException e) {
/* 199 */         e.printStackTrace();
/* 200 */       } catch (IOException e) {
/* 201 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 205 */     return null;
/*     */   }
/*     */   
/*     */   public List<LibraryId> getBlueprintIds() {
/* 209 */     return Collections.unmodifiableList(new ArrayList<LibraryId>(this.blueprintIds));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\LibraryDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */