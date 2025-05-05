/*     */ package buildcraft.transport.schematics;
/*     */ 
/*     */ import buildcraft.api.blueprints.BuildingPermission;
/*     */ import buildcraft.api.blueprints.IBuilderContext;
/*     */ import buildcraft.api.blueprints.MappingNotFoundException;
/*     */ import buildcraft.api.blueprints.MappingRegistry;
/*     */ import buildcraft.api.blueprints.Schematic;
/*     */ import buildcraft.api.blueprints.SchematicTile;
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.StatementManager;
/*     */ import buildcraft.transport.BlockGenericPipe;
/*     */ import buildcraft.transport.Gate;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.TileGenericPipe;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchematicPipe
/*     */   extends SchematicTile
/*     */ {
/*  36 */   private BuildingPermission permission = BuildingPermission.ALL;
/*     */ 
/*     */   
/*     */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/*  40 */     Pipe<?> pipe = BlockGenericPipe.getPipe((IBlockAccess)context.world(), x, y, z);
/*     */     
/*  42 */     if (BlockGenericPipe.isValid(pipe)) {
/*  43 */       return (pipe.item == Item.func_150899_d(this.tileNBT.func_74762_e("pipeId")));
/*     */     }
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotateLeft(IBuilderContext context) {
/*  51 */     TileGenericPipe.SideProperties props = new TileGenericPipe.SideProperties();
/*     */     
/*  53 */     props.readFromNBT(this.tileNBT);
/*  54 */     props.rotateLeft();
/*  55 */     props.writeToNBT(this.tileNBT);
/*     */     
/*  57 */     Item pipeItem = Item.func_150899_d(this.tileNBT.func_74762_e("pipeId"));
/*     */     
/*  59 */     if (BptPipeExtension.contains(pipeItem)) {
/*  60 */       BptPipeExtension.get(pipeItem).rotateLeft(this, context);
/*     */     }
/*     */     
/*  63 */     if (this.tileNBT.func_74764_b("Gate")) {
/*     */ 
/*     */ 
/*     */       
/*  67 */       NBTTagCompound gateNBT = this.tileNBT.func_74775_l("Gate");
/*  68 */       rotateGateLeft(gateNBT);
/*     */     }
/*     */     else {
/*     */       
/*  72 */       NBTTagCompound[] gatesNBT = new NBTTagCompound[6];
/*     */       int i;
/*  74 */       for (i = 0; i < 6; i++) {
/*  75 */         if (this.tileNBT.func_74764_b("Gate[" + i + "]")) {
/*  76 */           gatesNBT[i] = this.tileNBT.func_74775_l("Gate[" + i + "]");
/*     */         }
/*     */       } 
/*     */       
/*  80 */       for (i = 0; i < 6; i++) {
/*  81 */         int newI = ForgeDirection.values()[i].getRotation(ForgeDirection.UP).ordinal();
/*     */         
/*  83 */         if (gatesNBT[i] != null) {
/*  84 */           rotateGateLeft(gatesNBT[i]);
/*  85 */           this.tileNBT.func_74782_a("Gate[" + newI + "]", (NBTBase)gatesNBT[i]);
/*     */         } else {
/*  87 */           this.tileNBT.func_82580_o("Gate[" + newI + "]");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void rotateGateLeft(NBTTagCompound gateNBT) {
/*  94 */     for (int i = 0; i < Gate.MAX_STATEMENTS; i++) {
/*  95 */       if (gateNBT.func_74764_b("trigger[" + i + "]")) {
/*  96 */         IStatement t = (IStatement)StatementManager.statements.get(gateNBT.func_74779_i("trigger[" + i + "]"));
/*  97 */         t = t.rotateLeft();
/*  98 */         gateNBT.func_74778_a("trigger[" + i + "]", t.getUniqueTag());
/*     */       } 
/*     */       
/* 101 */       if (gateNBT.func_74764_b("action[" + i + "]")) {
/* 102 */         IStatement a = (IStatement)StatementManager.statements.get(gateNBT.func_74779_i("action[" + i + "]"));
/* 103 */         a = a.rotateLeft();
/* 104 */         gateNBT.func_74778_a("action[" + i + "]", a.getUniqueTag());
/*     */       } 
/*     */       
/* 107 */       for (int j = 0; j < Gate.MAX_PARAMETERS; j++) {
/* 108 */         if (gateNBT.func_74764_b("triggerParameters[" + i + "][" + j + "]")) {
/* 109 */           NBTTagCompound cpt = gateNBT.func_74775_l("triggerParameters[" + i + "][" + j + "]");
/* 110 */           IStatementParameter parameter = StatementManager.createParameter(cpt.func_74779_i("kind"));
/* 111 */           parameter.readFromNBT(cpt);
/*     */           
/* 113 */           parameter = parameter.rotateLeft();
/*     */           
/* 115 */           parameter.writeToNBT(cpt);
/* 116 */           gateNBT.func_74782_a("triggerParameters[" + i + "][" + j + "]", (NBTBase)cpt);
/*     */         } 
/*     */         
/* 119 */         if (gateNBT.func_74764_b("actionParameters[" + i + "][" + j + "]")) {
/* 120 */           NBTTagCompound cpt = gateNBT.func_74775_l("actionParameters[" + i + "][" + j + "]");
/* 121 */           IStatementParameter parameter = StatementManager.createParameter(cpt.func_74779_i("kind"));
/* 122 */           parameter.readFromNBT(cpt);
/*     */           
/* 124 */           parameter = parameter.rotateLeft();
/*     */           
/* 126 */           parameter.writeToNBT(cpt);
/* 127 */           gateNBT.func_74782_a("actionParameters[" + i + "][" + j + "]", (NBTBase)cpt);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     if (gateNBT.func_74764_b("direction")) {
/* 133 */       gateNBT.func_74768_a("direction", 
/* 134 */           ForgeDirection.values()[gateNBT.func_74762_e("direction")]
/* 135 */           .getRotation(ForgeDirection.UP).ordinal());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 141 */     this.tileNBT.func_74768_a("x", x);
/* 142 */     this.tileNBT.func_74768_a("y", y);
/* 143 */     this.tileNBT.func_74768_a("z", z);
/*     */     
/* 145 */     context.world().func_147465_d(x, y, z, this.block, this.meta, 3);
/*     */     
/* 147 */     TileEntity tile = context.world().func_147438_o(x, y, z);
/* 148 */     tile.func_145839_a(this.tileNBT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/* 153 */     TileEntity tile = context.world().func_147438_o(x, y, z);
/* 154 */     Pipe<?> pipe = BlockGenericPipe.getPipe((IBlockAccess)context.world(), x, y, z);
/*     */     
/* 156 */     if (BlockGenericPipe.isValid(pipe)) {
/* 157 */       tile.func_145841_b(this.tileNBT);
/*     */ 
/*     */ 
/*     */       
/* 161 */       this.tileNBT.func_82580_o("travelingEntities");
/*     */       
/* 163 */       for (ForgeDirection direction : ForgeDirection.values()) {
/* 164 */         this.tileNBT.func_82580_o("tank[" + direction.ordinal() + "]");
/* 165 */         this.tileNBT.func_82580_o("transferState[" + direction.ordinal() + "]");
/*     */       } 
/*     */       
/* 168 */       for (int i = 0; i < 6; i++) {
/* 169 */         this.tileNBT.func_82580_o("powerQuery[" + i + "]");
/* 170 */         this.tileNBT.func_82580_o("nextPowerQuery[" + i + "]");
/* 171 */         this.tileNBT.func_82580_o("internalPower[" + i + "]");
/* 172 */         this.tileNBT.func_82580_o("internalNextPower[" + i + "]");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {
/* 179 */     Pipe<?> pipe = BlockGenericPipe.getPipe((IBlockAccess)context.world(), x, y, z);
/*     */     
/* 181 */     if (BlockGenericPipe.isValid(pipe)) {
/* 182 */       ArrayList<ItemStack> items = pipe.computeItemDrop();
/* 183 */       this.storedRequirements = new ItemStack[items.size() + 1];
/* 184 */       items.toArray(this.storedRequirements);
/* 185 */       this.storedRequirements[this.storedRequirements.length - 1] = new ItemStack(pipe.item, 1, pipe.container
/* 186 */           .getItemMetadata());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcessing(IBuilderContext context, int x, int y, int z) {
/* 192 */     Item pipeItem = Item.func_150899_d(this.tileNBT.func_74762_e("pipeId"));
/*     */     
/* 194 */     if (BptPipeExtension.contains(pipeItem)) {
/* 195 */       BptPipeExtension.get(pipeItem).postProcessing(this, context);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Schematic.BuildingStage getBuildStage() {
/* 201 */     return Schematic.BuildingStage.STANDALONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void idsToBlueprint(MappingRegistry registry) {
/* 206 */     super.idsToBlueprint(registry);
/*     */     
/* 208 */     if (this.tileNBT.func_74764_b("pipeId")) {
/* 209 */       Item item = Item.func_150899_d(this.tileNBT.func_74762_e("pipeId"));
/*     */       
/* 211 */       this.tileNBT.func_74768_a("pipeId", registry.getIdForItem(item));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void idsToWorld(MappingRegistry registry) {
/* 217 */     super.idsToWorld(registry);
/*     */     
/* 219 */     if (this.tileNBT.func_74764_b("pipeId")) {
/*     */       try {
/* 221 */         Item item = registry.getItemForId(this.tileNBT.func_74762_e("pipeId"));
/*     */         
/* 223 */         this.tileNBT.func_74768_a("pipeId", Item.func_150891_b(item));
/* 224 */       } catch (MappingNotFoundException e) {
/* 225 */         this.tileNBT.func_82580_o("pipeId");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 232 */     super.writeSchematicToNBT(nbt, registry);
/* 233 */     nbt.func_74768_a("version", 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
/* 238 */     super.readSchematicFromNBT(nbt, registry);
/*     */     
/* 240 */     if (!nbt.func_74764_b("version") || nbt.func_74762_e("version") < 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       this.tileNBT.func_82580_o("items");
/*     */       
/* 247 */       if (this.tileNBT.func_74764_b("Gate")) {
/* 248 */         NBTTagCompound gateNBT = this.tileNBT.func_74775_l("Gate");
/*     */         
/* 250 */         for (int i = 0; i < 8; i++) {
/* 251 */           if (gateNBT.func_74764_b("triggerParameters[" + i + "]")) {
/* 252 */             NBTTagCompound parameterNBT = gateNBT.func_74775_l("triggerParameters[" + i + "]");
/*     */             
/* 254 */             if (parameterNBT.func_74764_b("stack")) {
/* 255 */               parameterNBT.func_82580_o("stack");
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BuildingPermission getBuildingPermission() {
/* 265 */     return this.permission;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\schematics\SchematicPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */