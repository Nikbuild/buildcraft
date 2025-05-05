/*     */ package buildcraft.api.statements;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StatementManager
/*     */ {
/*  22 */   public static Map<String, IStatement> statements = new HashMap<>();
/*  23 */   public static Map<String, IParameterReader> parameters = new HashMap<>();
/*  24 */   public static Map<String, IParamReaderBuf> paramsBuf = new HashMap<>();
/*  25 */   private static List<ITriggerProvider> triggerProviders = new LinkedList<>();
/*  26 */   private static List<IActionProvider> actionProviders = new LinkedList<>();
/*     */   
/*     */   static {
/*  29 */     registerParameter(StatementParameterItemStack::new);
/*     */   }
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
/*     */   public static void registerTriggerProvider(ITriggerProvider provider) {
/*  46 */     if (provider != null && !triggerProviders.contains(provider)) {
/*  47 */       triggerProviders.add(provider);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void registerActionProvider(IActionProvider provider) {
/*  52 */     if (provider != null && !actionProviders.contains(provider)) {
/*  53 */       actionProviders.add(provider);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void registerStatement(IStatement statement) {
/*  58 */     statements.put(statement.getUniqueTag(), statement);
/*     */   }
/*     */   
/*     */   public static void registerParameter(IParameterReader reader) {
/*  62 */     registerParameter(reader, buf -> reader.readFromNbt(buf.func_150793_b()));
/*     */   }
/*     */   
/*     */   public static void registerParameter(IParameterReader reader, IParamReaderBuf bufReader) {
/*  66 */     String name = reader.readFromNbt(new NBTTagCompound()).getUniqueTag();
/*  67 */     registerParameter(name, reader);
/*  68 */     registerParameter(name, bufReader);
/*     */   }
/*     */   
/*     */   public static void registerParameter(String name, IParameterReader reader) {
/*  72 */     parameters.put(name, reader);
/*     */   }
/*     */   
/*     */   public static void registerParameter(String name, IParamReaderBuf reader) {
/*  76 */     paramsBuf.put(name, reader);
/*     */   }
/*     */   
/*     */   public static List<ITriggerExternal> getExternalTriggers(EnumFacing side, TileEntity entity) {
/*  80 */     if (entity instanceof IOverrideDefaultStatements) {
/*  81 */       List<ITriggerExternal> result = ((IOverrideDefaultStatements)entity).overrideTriggers();
/*  82 */       if (result != null) {
/*  83 */         return result;
/*     */       }
/*     */     } 
/*     */     
/*  87 */     LinkedHashSet<ITriggerExternal> triggers = new LinkedHashSet<>();
/*     */     
/*  89 */     for (ITriggerProvider provider : triggerProviders) {
/*  90 */       provider.addExternalTriggers(triggers, side, entity);
/*     */     }
/*     */     
/*  93 */     return new ArrayList<>(triggers);
/*     */   }
/*     */   
/*     */   public static List<IActionExternal> getExternalActions(EnumFacing side, TileEntity entity) {
/*  97 */     if (entity instanceof IOverrideDefaultStatements) {
/*  98 */       List<IActionExternal> result = ((IOverrideDefaultStatements)entity).overrideActions();
/*  99 */       if (result != null) {
/* 100 */         return result;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     LinkedHashSet<IActionExternal> actions = new LinkedHashSet<>();
/*     */     
/* 106 */     for (IActionProvider provider : actionProviders) {
/* 107 */       provider.addExternalActions(actions, side, entity);
/*     */     }
/*     */     
/* 110 */     return new ArrayList<>(actions);
/*     */   }
/*     */   
/*     */   public static List<ITriggerInternal> getInternalTriggers(IStatementContainer container) {
/* 114 */     LinkedHashSet<ITriggerInternal> triggers = new LinkedHashSet<>();
/*     */     
/* 116 */     for (ITriggerProvider provider : triggerProviders) {
/* 117 */       provider.addInternalTriggers(triggers, container);
/*     */     }
/*     */     
/* 120 */     return new ArrayList<>(triggers);
/*     */   }
/*     */   
/*     */   public static List<IActionInternal> getInternalActions(IStatementContainer container) {
/* 124 */     LinkedHashSet<IActionInternal> actions = new LinkedHashSet<>();
/*     */     
/* 126 */     for (IActionProvider provider : actionProviders) {
/* 127 */       provider.addInternalActions(actions, container);
/*     */     }
/*     */     
/* 130 */     return new ArrayList<>(actions);
/*     */   }
/*     */   
/*     */   public static List<ITriggerInternalSided> getInternalSidedTriggers(IStatementContainer container, EnumFacing side) {
/* 134 */     LinkedHashSet<ITriggerInternalSided> triggers = new LinkedHashSet<>();
/*     */     
/* 136 */     for (ITriggerProvider provider : triggerProviders) {
/* 137 */       provider.addInternalSidedTriggers(triggers, container, side);
/*     */     }
/*     */     
/* 140 */     return new ArrayList<>(triggers);
/*     */   }
/*     */   
/*     */   public static List<IActionInternalSided> getInternalSidedActions(IStatementContainer container, EnumFacing side) {
/* 144 */     LinkedHashSet<IActionInternalSided> actions = new LinkedHashSet<>();
/*     */     
/* 146 */     for (IActionProvider provider : actionProviders) {
/* 147 */       provider.addInternalSidedActions(actions, container, side);
/*     */     }
/*     */     
/* 150 */     return new ArrayList<>(actions);
/*     */   }
/*     */   
/*     */   public static IParameterReader getParameterReader(String kind) {
/* 154 */     return parameters.get(kind);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface IParamReaderBuf {
/*     */     IStatementParameter readFromBuf(PacketBuffer param1PacketBuffer) throws IOException;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface IParameterReader {
/*     */     IStatementParameter readFromNbt(NBTTagCompound param1NBTTagCompound);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\StatementManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */