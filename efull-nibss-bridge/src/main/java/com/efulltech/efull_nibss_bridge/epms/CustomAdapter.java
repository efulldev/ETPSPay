/*    */ package com.efulltech.efull_nibss_bridge.epms;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import android.view.ViewGroup;
/*    */ import android.widget.BaseAdapter;
/*    */ import android.widget.ImageView;
/*    */ import android.widget.TextView;

        import com.efulltech.efull_nibss_bridge.R;

/*    */
/*    */ public class CustomAdapter
/*    */   extends BaseAdapter
/*    */ {
/*    */   Context context;
/*    */   String[] menuList;
/*    */   int[] icons;
/*    */   LayoutInflater inflter;
/*    */   
/*    */   public CustomAdapter(Context applicationContext, String[] List, int[] icons) {
/* 21 */     this.context = this.context;
/* 22 */     this.menuList = List;
/* 23 */     this.icons = icons;
/* 24 */     this.inflter = LayoutInflater.from(applicationContext);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public int getCount() { return this.menuList.length; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public Object getItem(int i) { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public long getItemId(int i) { return 0L; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public View getView(int i, View view, ViewGroup viewGroup) {
/* 45 */     view = this.inflter.inflate(R.layout.activity_menu, null);
/*    */     
/* 47 */     TextView menuitem = (TextView)view.findViewById(R.id.textView3);
/* 48 */     ImageView icon = (ImageView)view.findViewById(R.id.imageView3);
/* 49 */     menuitem.setTextColor(-16777216);
/* 50 */     menuitem.setText(this.menuList[i]);
/* 51 */     icon.setImageResource(this.icons[i]);
/*    */     
/* 53 */     return view;
/*    */   }
/*    */ }


/* Location:              /Users/MAC/Documents/classes.jar!/com/arke/sdk/util/epms/CustomAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */