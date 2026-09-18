package com.bashgahman.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.content.*;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(7,16,24));
        getWindow().setNavigationBarColor(Color.rgb(7,16,24));
        setContentView(new GymView(this));
    }
}

class GymView extends View {
    Paint p = new Paint(3);
    int page = 0;
    final int BG=Color.rgb(7,16,24), CARD=Color.rgb(16,32,43), YELLOW=Color.rgb(255,196,0), ORANGE=Color.rgb(255,122,0), WHITE=Color.rgb(247,250,252), MUTED=Color.rgb(157,176,188);
    String[] titles={"خانه","تمرین‌ها","تیپ بدنی","مکمل‌ها","برنامه","پیشرفت","درباره"};
    String[] icons={"⌂","🏋","◉","◆","▣","↗","ⓘ"};

    GymView(Context c){ super(c); setLayerType(View.LAYER_TYPE_SOFTWARE,null); }
    void rect(Canvas c,float l,float t,float r,float b,float rad,int color){ p.setColor(color);p.setStyle(Paint.Style.FILL);c.drawRoundRect(l,t,r,b,rad,rad,p); }
    void text(Canvas c,String s,float x,float y,float size,int color,Paint.Align a){p.setColor(color);p.setTextSize(size);p.setTextAlign(a);c.drawText(s,x,y,p);}
    void rtl(Canvas c,String s,float x,float y,float size,int color){text(c,s,x,y,size,color,Paint.Align.RIGHT);}
    void card(Canvas c,float l,float t,float r,float b){p.setShadowLayer(18,0,8,0x70000000);rect(c,l,t,r,b,24,CARD);p.clearShadowLayer();}
    void header(Canvas c,String a,String b){rtl(c,a,getWidth()-28,62,28,WHITE);rtl(c,b,getWidth()-28,90,14,MUTED);}
    @Override protected void onDraw(Canvas c){
        c.drawColor(BG);
        if(page==0) home(c); else if(page==1) workouts(c); else if(page==2) bodies(c); else if(page==3) supplements(c); else if(page==4) plans(c); else if(page==5) progress(c); else about(c);
        bottom(c);
    }
    void home(Canvas c){
        header(c,"باشگاه من","همه‌چیز برای یک بدن سالم و قوی");
        card(c,20,115,getWidth()-20,285);
        rtl(c,"قدرت فقط در عضلات نیست،",getWidth()-45,160,23,WHITE);
        rtl(c,"در اراده است...",getWidth()-45,198,30,YELLOW);
        rtl(c,"امروز یک قدم به هدف نزدیک‌تر شو.",getWidth()-45,230,15,MUTED);
        rect(c,45,250,getWidth()-45,270,12,YELLOW);
        rtl(c,"دسترسی سریع",getWidth()-28,330,22,WHITE);
        item(c,20,350,getWidth()/2-8,465,"تمرین‌ها","بانک حرکات","🏋",YELLOW);
        item(c,getWidth()/2+8,350,getWidth()-20,465,"مکمل‌ها","آشنایی و آموزش","◆",ORANGE);
        item(c,20,480,getWidth()/2-8,595,"تیپ بدنی","شناخت بدن","◉",0xff32c8ff);
        item(c,getWidth()/2+8,480,getWidth()-20,595,"پیشرفت","ثبت تغییرات","↗",0xff7c4dff);
    }
    void item(Canvas c,float l,float t,float r,float b,String a,String d,String i,int ac){card(c,l,t,r,b);text(c,i,l+24,t+45,30,ac,Paint.Align.LEFT);rtl(c,a,r-18,t+48,19,WHITE);rtl(c,d,r-18,t+77,13,MUTED);}
    void workouts(Canvas c){header(c,"تمرین‌ها","حرکات را بر اساس عضله پیدا کن");String[] a={"سینه","پشت","سرشانه","جلو بازو","پشت بازو","پا","باسن","شکم","ساق"};for(int i=0;i<a.length;i++){float l=20+(i%2)*(getWidth()/2-5),t=115+(i/2)*88,r=l+getWidth()/2-15;card(c,l,t,r,t+72);rtl(c,a[i],r-18,t+30,18,WHITE);rtl(c,"آموزش • فرم صحیح • نکات",r-18,t+55,11,MUTED);}}
    void bodies(Canvas c){header(c,"تیپ‌های بدنی","مدلی ساده برای آشنایی با تفاوت‌های بدنی");info(c,20,120,getWidth()-20,245,"اکتومورف","بدن معمولاً باریک‌تر؛ تمرکز بر انرژی کافی و پیشرفت تدریجی.");info(c,20,260,getWidth()-20,385,"مزومورف","ساختار عضلانی‌تر در بسیاری از افراد؛ برنامه منظم مهم است.");info(c,20,400,getWidth()-20,525,"اندومورف","گرایش بیشتر به ذخیره چربی در برخی افراد؛ ترکیب تمرین مقاومتی و هوازی.");rtl(c,"نکته علمی",getWidth()-28,565,18,YELLOW);rtl(c,"این دسته‌بندی‌ها قطعی یا تشخیصی نیستند.",getWidth()-28,593,12,MUTED);}
    void info(Canvas c,float l,float t,float r,float b,String h,String d){card(c,l,t,r,b);rtl(c,h,r-18,t+34,21,YELLOW);rtl(c,d,r-18,t+68,13,WHITE);}
    void supplements(Canvas c){header(c,"مکمل‌ها","معرفی، کاربرد، شواهد و احتیاط‌ها");String[] a={"پروتئین وی","کراتین","کافئین","BCAA","گینر","بتاآلانین"};for(int i=0;i<a.length;i++){float t=115+i*70;card(c,20,t,getWidth()-20,t+58);rtl(c,a[i],getWidth()-40,t+25,18,WHITE);rtl(c,"معرفی • کاربرد • احتیاط‌ها",getWidth()-40,t+47,11,MUTED);}}
    void plans(Canvas c){header(c,"برنامه تمرینی","برنامه‌های نمونه برای شروع");info(c,20,120,getWidth()-20,225,"مبتدی","۳ روز در هفته • تمرکز بر فرم صحیح و پیشرفت تدریجی");info(c,20,240,getWidth()-20,345,"متوسط","۴ روز در هفته • تقسیم عضلات و مدیریت حجم تمرین");info(c,20,360,getWidth()-20,465,"پیشرفته","۵ روز در هفته • نیازمند تجربه و تنظیم فردی");rect(c,20,495,getWidth()-20,555,18,YELLOW);rtl(c,"شروع برنامه انتخابی",getWidth()-45,533,17,BG);}
    void progress(Canvas c){header(c,"پیشرفت من","ثبت ساده تغییرات بدن");card(c,20,120,getWidth()-20,330);rtl(c,"وزن فعلی",getWidth()-45,165,16,MUTED);rtl(c,"۷۵ کیلو",getWidth()-45,205,30,WHITE);rtl(c,"قدرت تمرینی",getWidth()-45,250,16,MUTED);rtl(c,"+۱۸٪",getWidth()-45,290,30,YELLOW);p.setColor(YELLOW);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(5);c.drawArc(55,145,165,255,-70,250,false,p);p.setStyle(Paint.Style.FILL);}
    void about(Canvas c){header(c,"درباره باشگاه من","نسخه آموزشی");card(c,20,120,getWidth()-20,430);rtl(c,"مقدمه",getWidth()-42,160,22,YELLOW);rtl(c,"باشگاه من راهنمای فارسی برای آشنایی",getWidth()-42,200,14,WHITE);rtl(c,"با تمرین‌های بدنسازی، اجرای صحیح حرکات،",getWidth()-42,228,14,WHITE);rtl(c,"تیپ‌های بدنی و مکمل‌هاست.",getWidth()-42,256,14,WHITE);rtl(c,"مطالب آموزشی جایگزین پزشک، مربی",getWidth()-42,305,13,MUTED);rtl(c,"یا متخصص تغذیه نیستند.",getWidth()-42,330,13,MUTED);rtl(c,"سازنده",getWidth()-42,375,18,YELLOW);rtl(c,"@apkmod",getWidth()-42,410,22,WHITE);}
    void bottom(Canvas c){float y=getHeight()-78,w=getWidth()/7f;rect(c,0,y,getWidth(),getHeight(),0,0xff0b1720);for(int i=0;i<7;i++){int col=i==page?YELLOW:MUTED;text(c,icons[i],w*i+w/2,y+30,21,col,Paint.Align.CENTER);text(c,titles[i],w*i+w/2,y+55,9,col,Paint.Align.CENTER);}}
    @Override public boolean onTouchEvent(MotionEvent e){if(e.getAction()!=MotionEvent.ACTION_UP)return true;if(e.getY()>getHeight()-100){page=Math.max(0,Math.min(6,(int)(e.getX()/(getWidth()/7f))));invalidate();}return true;}
}
