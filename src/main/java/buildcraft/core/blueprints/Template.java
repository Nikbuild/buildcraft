/*    */ package buildcraft.core.blueprints;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicBlockBase;
/*    */ import buildcraft.api.blueprints.SchematicMask;
/*    */ import buildcraft.api.core.BuildCraftAPI;
/*    */ import buildcraft.core.lib.utils.NBTUtils;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Template
/*    */   extends BlueprintBase
/*    */ {
/*    */   public Template() {
/* 27 */     this.id.extension = "tpl";
/*    */   }
/*    */   
/*    */   public Template(int sizeX, int sizeY, int sizeZ) {
/* 31 */     super(sizeX, sizeY, sizeZ);
/*    */     
/* 33 */     this.id.extension = "tpl";
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromWorld(IBuilderContext context, TileEntity anchorTile, int x, int y, int z) {
/* 38 */     int posX = (int)(x - (context.surroundingBox().pMin()).x);
/* 39 */     int posY = (int)(y - (context.surroundingBox().pMin()).y);
/* 40 */     int posZ = (int)(z - (context.surroundingBox().pMin()).z);
/*    */     
/* 42 */     if (!BuildCraftAPI.isSoftBlock(anchorTile.func_145831_w(), x, y, z)) {
/* 43 */       put(posX, posY, posZ, (SchematicBlockBase)new SchematicMask(true));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveContents(NBTTagCompound nbt) {
/* 53 */     byte[] data = new byte[this.sizeX * this.sizeY * this.sizeZ];
/* 54 */     int ind = 0;
/*    */     
/* 56 */     for (int x = 0; x < this.sizeX; x++) {
/* 57 */       for (int y = 0; y < this.sizeY; y++) {
/* 58 */         for (int z = 0; z < this.sizeZ; z++) {
/* 59 */           data[ind] = (byte)((get(x, y, z) == null) ? 0 : 1);
/* 60 */           ind++;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     nbt.func_74773_a("mask", data);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadContents(NBTTagCompound nbt) throws BptError {
/* 70 */     byte[] data = nbt.func_74770_j("mask");
/* 71 */     int ind = 0;
/*    */     
/* 73 */     for (int x = 0; x < this.sizeX; x++) {
/* 74 */       for (int y = 0; y < this.sizeY; y++) {
/* 75 */         for (int z = 0; z < this.sizeZ; z++) {
/* 76 */           if (data[ind] == 1) {
/* 77 */             put(x, y, z, (SchematicBlockBase)new SchematicMask(true));
/*    */           }
/*    */           
/* 80 */           ind++;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getStack() {
/* 88 */     Item item = (Item)Item.field_150901_e.func_82594_a("BuildCraft|Builders:templateItem");
/* 89 */     ItemStack stack = new ItemStack(item, 1);
/* 90 */     NBTTagCompound nbt = NBTUtils.getItemData(stack);
/* 91 */     this.id.write(nbt);
/* 92 */     nbt.func_74778_a("author", this.author);
/* 93 */     nbt.func_74778_a("name", this.id.name);
/*    */     
/* 95 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\Template.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */