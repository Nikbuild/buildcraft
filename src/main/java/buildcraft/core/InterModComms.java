/*     */ package buildcraft.core;
/*     */ 
/*     */ import buildcraft.api.core.BCLog;
/*     */ import buildcraft.core.crops.CropHandlerPlantable;
/*     */ import buildcraft.core.recipes.AssemblyRecipeManager;
/*     */ import buildcraft.core.recipes.RefineryRecipeManager;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import cpw.mods.fml.common.event.FMLInterModComms;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ public final class InterModComms
/*     */ {
/*  31 */   private static final Set<IMCHandler> handlers = new HashSet<IMCHandler>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerHandler(IMCHandler handler) {
/*  40 */     handlers.add(handler);
/*     */   }
/*     */   
/*     */   public static void processIMC(FMLInterModComms.IMCEvent event) {
/*  44 */     for (UnmodifiableIterator<FMLInterModComms.IMCMessage> unmodifiableIterator = event.getMessages().iterator(); unmodifiableIterator.hasNext(); ) { FMLInterModComms.IMCMessage m = unmodifiableIterator.next();
/*  45 */       if (m.key.equals("add-assembly-recipe")) {
/*  46 */         processAssemblyRecipeAddIMC(event, m); continue;
/*  47 */       }  if (m.key.equals("add-refinery-recipe")) {
/*  48 */         processRefineryRecipeAddIMC(event, m); continue;
/*  49 */       }  if (m.key.equals("remove-assembly-recipe")) {
/*  50 */         processAssemblyRecipeRemoveIMC(event, m); continue;
/*  51 */       }  if (m.key.equals("remove-refinery-recipe")) {
/*  52 */         processRefineryRecipeRemoveIMC(event, m); continue;
/*  53 */       }  if (m.key.equals("remove-plantable-block")) {
/*  54 */         processPlantableBlockRemoveIMC(event, m); continue;
/*     */       } 
/*  56 */       for (IMCHandler h : handlers) {
/*  57 */         h.processIMCEvent(event, m);
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static void processPlantableBlockRemoveIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage msg) {
/*  64 */     if (msg.isStringMessage()) {
/*  65 */       Object blockObj = Block.field_149771_c.func_82594_a(msg.getStringValue());
/*  66 */       if (blockObj instanceof Block) {
/*  67 */         CropHandlerPlantable.forbidBlock((Block)blockObj);
/*     */       }
/*  69 */       BCLog.logger.info(String.format("Received a plantable block '%s' removal request from mod %s", new Object[] { msg.getStringValue(), msg.getSender() }));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void processAssemblyRecipeRemoveIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage msg) {
/*  74 */     if (msg.isStringMessage()) {
/*  75 */       AssemblyRecipeManager.INSTANCE.removeRecipe(msg.getStringValue());
/*     */       
/*  77 */       BCLog.logger.info(String.format("Received an assembly recipe '%s' removal request from mod %s", new Object[] { msg.getStringValue(), msg.getSender() }));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void processRefineryRecipeRemoveIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage msg) {
/*  82 */     if (msg.isStringMessage()) {
/*  83 */       RefineryRecipeManager.INSTANCE.removeRecipe(msg.getStringValue());
/*     */       
/*  85 */       BCLog.logger.info(String.format("Received a refinery recipe '%s' removal request from mod %s", new Object[] { msg.getStringValue(), msg.getSender() }));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void processAssemblyRecipeAddIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage msg) {
/*  90 */     boolean failed = false;
/*  91 */     if (!msg.isNBTMessage()) {
/*  92 */       failed = true;
/*     */     } else {
/*  94 */       NBTTagCompound recipe = msg.getNBTValue();
/*  95 */       if (!recipe.func_74764_b("id") || !recipe.func_150297_b("input", 9) || !recipe.func_150297_b("output", 10) || 
/*  96 */         !recipe.func_150297_b("energy", 3)) {
/*  97 */         failed = true;
/*     */       } else {
/*  99 */         NBTTagList list = (NBTTagList)recipe.func_74781_a("input");
/* 100 */         List<ItemStack> input = new ArrayList<ItemStack>();
/* 101 */         for (int i = 0; i < list.func_74745_c(); i++) {
/* 102 */           ItemStack itemStack = ItemStack.func_77949_a(list.func_150305_b(i));
/* 103 */           if (itemStack != null) {
/* 104 */             input.add(itemStack);
/*     */           }
/*     */         } 
/* 107 */         String id = recipe.func_74779_i("id");
/* 108 */         ItemStack is = ItemStack.func_77949_a(recipe.func_74775_l("output"));
/* 109 */         if (is != null && !input.isEmpty() && id.length() > 0) {
/* 110 */           AssemblyRecipeManager.INSTANCE.addRecipe(id, recipe.func_74762_e("energy"), is, input
/* 111 */               .<Object>toArray((Object[])new ItemStack[input.size()]));
/*     */         } else {
/* 113 */           failed = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 117 */     if (failed) {
/* 118 */       BCLog.logger.warn("Received invalid assembly recipe IMC message from mod %s!", new Object[] { msg.getSender() });
/*     */     }
/*     */   }
/*     */   
/*     */   public static void processRefineryRecipeAddIMC(FMLInterModComms.IMCEvent event, FMLInterModComms.IMCMessage msg) {
/* 123 */     boolean failed = false;
/* 124 */     if (!msg.isNBTMessage()) {
/* 125 */       failed = true;
/*     */     } else {
/* 127 */       NBTTagCompound recipe = msg.getNBTValue();
/* 128 */       if ((!recipe.func_74764_b("id") && !recipe.func_150297_b("input", 10)) || !recipe.func_150297_b("output", 10) || 
/* 129 */         !recipe.func_150297_b("energy", 3) || !recipe.func_150297_b("delay", 3)) {
/* 130 */         failed = true;
/*     */       } else {
/* 132 */         FluidStack output = FluidStack.loadFluidStackFromNBT(recipe.func_74775_l("output"));
/* 133 */         FluidStack input = FluidStack.loadFluidStackFromNBT(recipe.func_74775_l("input"));
/* 134 */         FluidStack input2 = null;
/* 135 */         String id = recipe.func_74779_i("id");
/* 136 */         if (recipe.func_150297_b("input_2", 10)) {
/* 137 */           input2 = FluidStack.loadFluidStackFromNBT(recipe.func_74775_l("input_2"));
/*     */         }
/* 139 */         if (input != null && output != null && id.length() > 0) {
/* 140 */           RefineryRecipeManager.INSTANCE.addRecipe(id, input, input2, output, recipe.func_74762_e("energy"), recipe
/* 141 */               .func_74762_e("delay"));
/*     */         } else {
/* 143 */           failed = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 147 */     if (failed)
/* 148 */       BCLog.logger.warn("Received invalid refinery recipe IMC message from mod %s!", new Object[] { msg.getSender() }); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\InterModComms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */