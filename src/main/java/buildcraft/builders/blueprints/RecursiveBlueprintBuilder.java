/*    */ package buildcraft.builders.blueprints;
/*    */ 
/*    */ import buildcraft.api.core.IAreaProvider;
/*    */ import buildcraft.core.Box;
/*    */ import buildcraft.core.blueprints.Blueprint;
/*    */ import buildcraft.core.blueprints.BlueprintBase;
/*    */ import buildcraft.core.blueprints.BptBuilderBase;
/*    */ import buildcraft.core.blueprints.BptBuilderBlueprint;
/*    */ import buildcraft.core.blueprints.BptBuilderTemplate;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ 
/*    */ public class RecursiveBlueprintBuilder
/*    */ {
/*    */   private boolean returnedThis = false;
/*    */   private BlueprintBase blueprint;
/*    */   private RecursiveBlueprintBuilder current;
/* 31 */   private int nextSubBlueprint = 0;
/* 32 */   private ArrayList<NBTTagCompound> subBlueprints = new ArrayList<NBTTagCompound>();
/*    */   
/*    */   private int x;
/*    */   private int y;
/* 36 */   private Box box = new Box(); private int z; private ForgeDirection dir;
/*    */   private World world;
/*    */   
/*    */   public RecursiveBlueprintBuilder(BlueprintBase iBlueprint, World iWorld, int iX, int iY, int iZ, ForgeDirection iDir) {
/* 40 */     this.blueprint = iBlueprint;
/* 41 */     this.subBlueprints = iBlueprint.subBlueprintsNBT;
/* 42 */     this.world = iWorld;
/* 43 */     this.x = iX;
/* 44 */     this.y = iY;
/* 45 */     this.z = iZ;
/* 46 */     this.dir = iDir;
/*    */   }
/*    */   
/*    */   public BptBuilderBase nextBuilder() {
/* 50 */     if (!this.returnedThis) {
/* 51 */       BptBuilderTemplate bptBuilderTemplate; this.blueprint = this.blueprint.adjustToWorld(this.world, this.x, this.y, this.z, this.dir);
/*    */       
/* 53 */       this.returnedThis = true;
/*    */ 
/*    */ 
/*    */       
/* 57 */       if (this.blueprint instanceof Blueprint) {
/* 58 */         BptBuilderBlueprint bptBuilderBlueprint = new BptBuilderBlueprint((Blueprint)this.blueprint, this.world, this.x, this.y, this.z);
/* 59 */       } else if (this.blueprint instanceof buildcraft.core.blueprints.Template) {
/* 60 */         bptBuilderTemplate = new BptBuilderTemplate(this.blueprint, this.world, this.x, this.y, this.z);
/*    */       } else {
/* 62 */         return null;
/*    */       } 
/*    */       
/* 65 */       this.box.initialize((IAreaProvider)bptBuilderTemplate);
/*    */       
/* 67 */       return (BptBuilderBase)bptBuilderTemplate;
/*    */     } 
/*    */ 
/*    */     
/* 71 */     this.blueprint = null;
/*    */     
/* 73 */     if (this.current != null) {
/* 74 */       BptBuilderBase builder = this.current.nextBuilder();
/*    */       
/* 76 */       if (builder != null) {
/* 77 */         return builder;
/*    */       }
/*    */     } 
/*    */     
/* 81 */     if (this.nextSubBlueprint >= this.subBlueprints.size()) {
/* 82 */       return null;
/*    */     }
/*    */     
/* 85 */     NBTTagCompound nbt = this.subBlueprints.get(this.nextSubBlueprint);
/* 86 */     BlueprintBase bpt = BlueprintBase.loadBluePrint(nbt.func_74775_l("bpt"));
/*    */     
/* 88 */     int nx = this.box.xMin + nbt.func_74762_e("x");
/* 89 */     int ny = this.box.yMin + nbt.func_74762_e("y");
/* 90 */     int nz = this.box.zMin + nbt.func_74762_e("z");
/*    */     
/* 92 */     ForgeDirection nbtDir = ForgeDirection.values()[nbt.func_74771_c("dir")];
/*    */     
/* 94 */     this.current = new RecursiveBlueprintBuilder(bpt, this.world, nx, ny, nz, nbtDir);
/* 95 */     this.nextSubBlueprint++;
/*    */     
/* 97 */     return this.current.nextBuilder();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\blueprints\RecursiveBlueprintBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */