/*     */ package buildcraft.transport.gates;
/*     */ 
/*     */ import buildcraft.api.gates.GateExpansionController;
/*     */ import buildcraft.api.gates.GateExpansions;
/*     */ import buildcraft.api.gates.IGateExpansion;
/*     */ import buildcraft.transport.Gate;
/*     */ import buildcraft.transport.Pipe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GateFactory
/*     */ {
/*     */   public static Gate makeGate(Pipe<?> pipe, GateDefinition.GateMaterial material, GateDefinition.GateLogic logic, ForgeDirection direction) {
/*  35 */     return new Gate(pipe, material, logic, direction);
/*     */   }
/*     */   
/*     */   public static Gate makeGate(Pipe<?> pipe, ItemStack stack, ForgeDirection direction) {
/*  39 */     if (stack == null || stack.field_77994_a <= 0 || !(stack.func_77973_b() instanceof ItemGate)) {
/*  40 */       return null;
/*     */     }
/*     */     
/*  43 */     Gate gate = makeGate(pipe, ItemGate.getMaterial(stack), ItemGate.getLogic(stack), direction);
/*     */     
/*  45 */     for (IGateExpansion expansion : ItemGate.getInstalledExpansions(stack)) {
/*  46 */       gate.addGateExpansion(expansion);
/*     */     }
/*     */     
/*  49 */     return gate;
/*     */   }
/*     */   
/*     */   public static Gate makeGate(Pipe<?> pipe, NBTTagCompound nbt) {
/*  53 */     GateDefinition.GateMaterial material = GateDefinition.GateMaterial.REDSTONE;
/*  54 */     GateDefinition.GateLogic logic = GateDefinition.GateLogic.AND;
/*  55 */     ForgeDirection direction = ForgeDirection.UNKNOWN;
/*     */ 
/*     */     
/*  58 */     if (nbt.func_74764_b("Kind")) {
/*  59 */       int kind = nbt.func_74762_e("Kind");
/*  60 */       switch (kind) {
/*     */         case 1:
/*     */         case 2:
/*  63 */           material = GateDefinition.GateMaterial.IRON;
/*     */           break;
/*     */         case 3:
/*     */         case 4:
/*  67 */           material = GateDefinition.GateMaterial.GOLD;
/*     */           break;
/*     */         case 5:
/*     */         case 6:
/*  71 */           material = GateDefinition.GateMaterial.DIAMOND;
/*     */           break;
/*     */       } 
/*  74 */       switch (kind) {
/*     */         case 2:
/*     */         case 4:
/*     */         case 6:
/*  78 */           logic = GateDefinition.GateLogic.OR;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/*  83 */     if (nbt.func_74764_b("material")) {
/*     */       try {
/*  85 */         material = GateDefinition.GateMaterial.valueOf(nbt.func_74779_i("material"));
/*  86 */       } catch (IllegalArgumentException ex) {
/*  87 */         return null;
/*     */       } 
/*     */     }
/*  90 */     if (nbt.func_74764_b("logic")) {
/*     */       try {
/*  92 */         logic = GateDefinition.GateLogic.valueOf(nbt.func_74779_i("logic"));
/*  93 */       } catch (IllegalArgumentException ex) {
/*  94 */         return null;
/*     */       } 
/*     */     }
/*  97 */     if (nbt.func_74764_b("direction")) {
/*  98 */       direction = ForgeDirection.getOrientation(nbt.func_74762_e("direction"));
/*     */     }
/*     */     
/* 101 */     Gate gate = makeGate(pipe, material, logic, direction);
/* 102 */     gate.readFromNBT(nbt);
/*     */ 
/*     */     
/* 105 */     if (nbt.func_74764_b("Pulser")) {
/* 106 */       NBTTagCompound pulsarTag = nbt.func_74775_l("Pulser");
/* 107 */       GateExpansionController pulsarCon = GateExpansionPulsar.INSTANCE.makeController((TileEntity)pipe.container);
/* 108 */       pulsarCon.readFromNBT(pulsarTag);
/* 109 */       gate.expansions.put(GateExpansionPulsar.INSTANCE, pulsarCon);
/*     */     } 
/*     */     
/* 112 */     NBTTagList exList = nbt.func_150295_c("expansions", 10);
/* 113 */     for (int i = 0; i < exList.func_74745_c(); i++) {
/* 114 */       NBTTagCompound conNBT = exList.func_150305_b(i);
/* 115 */       IGateExpansion ex = GateExpansions.getExpansion(conNBT.func_74779_i("type"));
/* 116 */       if (ex != null) {
/* 117 */         GateExpansionController con = ex.makeController((TileEntity)pipe.container);
/* 118 */         con.readFromNBT(conNBT.func_74775_l("data"));
/* 119 */         gate.expansions.put(ex, con);
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     return gate;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GateFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */