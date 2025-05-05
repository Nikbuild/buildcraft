/*     */ package buildcraft.transport.statements;
/*     */ 
/*     */ import buildcraft.api.gates.IGate;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerInternal;
/*     */ import buildcraft.api.statements.StatementParameterItemStack;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.core.statements.BCStatement;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeTransportFluids;
/*     */ import buildcraft.transport.PipeTransportItems;
/*     */ import buildcraft.transport.PipeTransportPower;
/*     */ import buildcraft.transport.TravelingItem;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TriggerPipeContents
/*     */   extends BCStatement
/*     */   implements ITriggerInternal
/*     */ {
/*     */   private PipeContents kind;
/*     */   
/*     */   public enum PipeContents
/*     */   {
/*  37 */     empty,
/*  38 */     containsItems,
/*  39 */     containsFluids,
/*  40 */     containsEnergy,
/*  41 */     requestsEnergy,
/*  42 */     tooMuchEnergy;
/*     */     
/*     */     public ITriggerInternal trigger;
/*     */   }
/*     */ 
/*     */   
/*     */   public TriggerPipeContents(PipeContents kind) {
/*  49 */     super(new String[] { "buildcraft:pipe.contents." + kind.name().toLowerCase(Locale.ENGLISH), "buildcraft.pipe.contents." + kind.name() });
/*  50 */     this.kind = kind;
/*  51 */     kind.trigger = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  56 */     switch (this.kind) {
/*     */       case containsItems:
/*     */       case containsFluids:
/*  59 */         return 1;
/*     */     } 
/*  61 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  67 */     return StringUtils.localize("gate.trigger.pipe." + this.kind.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(IStatementContainer container, IStatementParameter[] parameters) {
/*  72 */     if (!(container instanceof IGate)) {
/*  73 */       return false;
/*     */     }
/*     */     
/*  76 */     Pipe<?> pipe = (Pipe)((IGate)container).getPipe();
/*  77 */     IStatementParameter parameter = parameters[0];
/*     */     
/*  79 */     if (pipe.transport instanceof PipeTransportItems) {
/*  80 */       PipeTransportItems transportItems = (PipeTransportItems)pipe.transport;
/*  81 */       if (this.kind == PipeContents.empty)
/*  82 */         return transportItems.items.isEmpty(); 
/*  83 */       if (this.kind == PipeContents.containsItems) {
/*  84 */         if (parameter != null && parameter.getItemStack() != null) {
/*  85 */           for (TravelingItem item : transportItems.items) {
/*  86 */             if (StackHelper.isMatchingItemOrList(parameter.getItemStack(), item.getItemStack())) {
/*  87 */               return true;
/*     */             }
/*     */           } 
/*     */         } else {
/*  91 */           return !transportItems.items.isEmpty();
/*     */         } 
/*     */       }
/*  94 */     } else if (pipe.transport instanceof PipeTransportFluids) {
/*  95 */       PipeTransportFluids transportFluids = (PipeTransportFluids)pipe.transport;
/*     */       
/*  97 */       if (this.kind == PipeContents.empty) {
/*  98 */         return (transportFluids.fluidType == null);
/*     */       }
/* 100 */       if (parameter != null && parameter.getItemStack() != null) {
/* 101 */         FluidStack searchedFluid = FluidContainerRegistry.getFluidForFilledItem(parameter.getItemStack());
/*     */         
/* 103 */         if (searchedFluid != null) {
/* 104 */           return (transportFluids.fluidType != null && searchedFluid.isFluidEqual(transportFluids.fluidType));
/*     */         }
/*     */       } else {
/* 107 */         return (transportFluids.fluidType != null);
/*     */       }
/*     */     
/* 110 */     } else if (pipe.transport instanceof PipeTransportPower) {
/* 111 */       PipeTransportPower transportPower = (PipeTransportPower)pipe.transport;
/*     */       
/* 113 */       switch (this.kind) {
/*     */         case empty:
/* 115 */           for (short s : transportPower.displayPower) {
/* 116 */             if (s > 0) {
/* 117 */               return false;
/*     */             }
/*     */           } 
/*     */           
/* 121 */           return true;
/*     */         case containsEnergy:
/* 123 */           for (short s : transportPower.displayPower) {
/* 124 */             if (s > 0) {
/* 125 */               return true;
/*     */             }
/*     */           } 
/*     */           
/* 129 */           return false;
/*     */         case requestsEnergy:
/* 131 */           return transportPower.isQueryingPower();
/*     */       } 
/*     */       
/* 134 */       return transportPower.isOverloaded();
/*     */     } 
/*     */ 
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/* 143 */     return (IStatementParameter)new StatementParameterItemStack();
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerIcons(IIconRegister iconRegister) {
/* 149 */     this.icon = iconRegister.func_94245_a("buildcrafttransport:triggers/trigger_pipecontents_" + this.kind.name().toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\statements\TriggerPipeContents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */