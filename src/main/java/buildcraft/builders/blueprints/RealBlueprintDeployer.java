/*    */ package buildcraft.builders.blueprints;
/*    */ 
/*    */ import buildcraft.api.blueprints.BlueprintDeployer;
/*    */ import buildcraft.api.blueprints.Translation;
/*    */ import buildcraft.builders.LibraryDatabase;
/*    */ import buildcraft.core.blueprints.Blueprint;
/*    */ import buildcraft.core.blueprints.BlueprintBase;
/*    */ import buildcraft.core.blueprints.BptBuilderBlueprint;
/*    */ import buildcraft.core.blueprints.BptContext;
/*    */ import buildcraft.core.blueprints.LibraryId;
/*    */ import buildcraft.core.lib.utils.NBTUtils;
/*    */ import java.io.File;
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
/*    */ 
/*    */ 
/*    */ public class RealBlueprintDeployer
/*    */   extends BlueprintDeployer
/*    */ {
/*    */   public void deployBlueprint(World world, int x, int y, int z, ForgeDirection dir, File file) {
/* 33 */     deployBlueprint(world, x, y, z, dir, (Blueprint)BlueprintBase.loadBluePrint(LibraryDatabase.load(file)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void deployBlueprintFromFileStream(World world, int x, int y, int z, ForgeDirection dir, byte[] data) {
/* 40 */     deployBlueprint(world, x, y, z, dir, (Blueprint)BlueprintBase.loadBluePrint(NBTUtils.load(data)));
/*    */   }
/*    */   
/*    */   private void deployBlueprint(World world, int x, int y, int z, ForgeDirection dir, Blueprint bpt) {
/* 44 */     bpt.id = new LibraryId();
/* 45 */     bpt.id.extension = "bpt";
/*    */     
/* 47 */     BptContext context = bpt.getContext(world, bpt.getBoxForPos(x, y, z));
/*    */     
/* 49 */     if (bpt.rotate && 
/* 50 */       dir != ForgeDirection.EAST)
/*    */     {
/* 52 */       if (dir == ForgeDirection.SOUTH) {
/* 53 */         bpt.rotateLeft(context);
/* 54 */       } else if (dir == ForgeDirection.WEST) {
/* 55 */         bpt.rotateLeft(context);
/* 56 */         bpt.rotateLeft(context);
/* 57 */       } else if (dir == ForgeDirection.NORTH) {
/* 58 */         bpt.rotateLeft(context);
/* 59 */         bpt.rotateLeft(context);
/* 60 */         bpt.rotateLeft(context);
/*    */       } 
/*    */     }
/*    */     
/* 64 */     Translation transform = new Translation();
/*    */     
/* 66 */     transform.x = (x - bpt.anchorX);
/* 67 */     transform.y = (y - bpt.anchorY);
/* 68 */     transform.z = (z - bpt.anchorZ);
/*    */     
/* 70 */     bpt.translateToWorld(transform);
/*    */     
/* 72 */     (new BptBuilderBlueprint(bpt, world, x, y, z)).deploy();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\blueprints\RealBlueprintDeployer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */