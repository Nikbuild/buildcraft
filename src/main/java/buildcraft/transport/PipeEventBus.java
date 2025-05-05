/*     */ package buildcraft.transport;
/*     */ 
/*     */ import buildcraft.transport.pipes.events.PipeEvent;
/*     */ import buildcraft.transport.pipes.events.PipeEventPriority;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PipeEventBus
/*     */ {
/*     */   private class EventHandler
/*     */   {
/*     */     public Method method;
/*     */     
/*     */     public EventHandler(Method m, Object o) {
/*  21 */       this.method = m;
/*  22 */       this.owner = o;
/*     */     }
/*     */     public Object owner;
/*     */     
/*     */     public boolean equals(Object o) {
/*  27 */       if (o == null || !(o instanceof EventHandler)) {
/*  28 */         return false;
/*     */       }
/*     */       
/*  31 */       EventHandler e = (EventHandler)o;
/*  32 */       return (e.method.equals(this.method) && e.owner == this.owner);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EventHandlerCompare implements Comparator<EventHandler> {
/*     */     private int getPriority(PipeEventBus.EventHandler eh) {
/*  38 */       PipeEventPriority p = eh.method.<PipeEventPriority>getAnnotation(PipeEventPriority.class);
/*  39 */       return (p != null) ? p.priority() : 0;
/*     */     }
/*     */     private EventHandlerCompare() {}
/*     */     
/*     */     public int compare(PipeEventBus.EventHandler o1, PipeEventBus.EventHandler o2) {
/*  44 */       int priority1 = getPriority(o1);
/*  45 */       int priority2 = getPriority(o2);
/*  46 */       return priority2 - priority1;
/*     */     }
/*     */   }
/*     */   
/*  50 */   private static final EventHandlerCompare COMPARATOR = new EventHandlerCompare();
/*  51 */   private static final HashSet<Object> globalHandlers = new HashSet();
/*     */   
/*  53 */   private final HashSet<Object> registeredHandlers = new HashSet();
/*  54 */   private final HashMap<Object, Map<Method, Class<? extends PipeEvent>>> handlerMethods = new HashMap<Object, Map<Method, Class<? extends PipeEvent>>>();
/*  55 */   private final HashMap<Class<? extends PipeEvent>, List<EventHandler>> eventHandlers = new HashMap<Class<? extends PipeEvent>, List<EventHandler>>();
/*     */   
/*     */   public PipeEventBus() {
/*  58 */     for (Object o : globalHandlers) {
/*  59 */       registerHandler(o);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void registerGlobalHandler(Object globalHandler) {
/*  64 */     globalHandlers.add(globalHandler);
/*     */   }
/*     */   
/*     */   private List<EventHandler> getHandlerList(Class<? extends PipeEvent> event) {
/*  68 */     if (!this.eventHandlers.containsKey(event)) {
/*  69 */       this.eventHandlers.put(event, new ArrayList<EventHandler>());
/*     */     }
/*  71 */     return this.eventHandlers.get(event);
/*     */   }
/*     */   
/*     */   public void registerHandler(Object handler) {
/*  75 */     if (this.registeredHandlers.contains(handler)) {
/*     */       return;
/*     */     }
/*     */     
/*  79 */     this.registeredHandlers.add(handler);
/*  80 */     Map<Method, Class<? extends PipeEvent>> methods = new HashMap<Method, Class<? extends PipeEvent>>();
/*     */     
/*  82 */     for (Method m : handler.getClass().getDeclaredMethods()) {
/*  83 */       if ("eventHandler".equals(m.getName())) {
/*  84 */         Class<?>[] parameters = m.getParameterTypes();
/*  85 */         if (parameters.length == 1 && PipeEvent.class.isAssignableFrom(parameters[0])) {
/*  86 */           Class<? extends PipeEvent> eventType = (Class)parameters[0];
/*  87 */           List<EventHandler> eventHandlerList = getHandlerList(eventType);
/*  88 */           eventHandlerList.add(new EventHandler(m, handler));
/*  89 */           updateEventHandlers(eventHandlerList);
/*  90 */           methods.put(m, eventType);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     this.handlerMethods.put(handler, methods);
/*     */   }
/*     */   
/*     */   private void updateEventHandlers(List<EventHandler> eventHandlerList) {
/*  99 */     Collections.sort(eventHandlerList, COMPARATOR);
/*     */   }
/*     */   
/*     */   public void unregisterHandler(Object handler) {
/* 103 */     if (!this.registeredHandlers.contains(handler)) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     this.registeredHandlers.remove(handler);
/* 108 */     Map<Method, Class<? extends PipeEvent>> methodMap = this.handlerMethods.get(handler);
/* 109 */     for (Method m : methodMap.keySet()) {
/* 110 */       getHandlerList(methodMap.get(m)).remove(new EventHandler(m, handler));
/*     */     }
/* 112 */     this.handlerMethods.remove(handler);
/*     */   }
/*     */   
/*     */   public void handleEvent(Class<? extends PipeEvent> eventClass, PipeEvent event) {
/* 116 */     for (EventHandler eventHandler : getHandlerList(eventClass)) {
/*     */       try {
/* 118 */         eventHandler.method.invoke(eventHandler.owner, new Object[] { event });
/* 119 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\PipeEventBus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */