package com.bashgahman.app;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(5,11,17));
        getWindow().setNavigationBarColor(Color.rgb(5,11,17));
        getWindow().getDecorView().setSystemUiVisibility(0);
        setContentView(new GymView(this));
    }
}

class GymView extends View {
    final int BG=Color.rgb(5,11,17), SURFACE=Color.rgb(12,22,30), CARD=Color.rgb(17,30,40),
            CARD2=Color.rgb(22,38,49), ACCENT=Color.rgb(255,193,7), ORANGE=Color.rgb(255,111,35),
            CYAN=Color.rgb(45,196,255), PURPLE=Color.rgb(151,105,255), WHITE=Color.rgb(246,249,251),
            MUTED=Color.rgb(145,164,176), GREEN=Color.rgb(67,210,143), RED=Color.rgb(255,92,92);
    Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
    MainActivity activity; int page=0, detail=-1; float scroll=0, downY, lastY; boolean moved;
    String[] nav={"خانه","تمرین","بدن","مکمل","برنامه","پیشرفت","درباره"};
    String[] navGlyph={"⌂","✦","◎","＋","▦","↗","i"};
    String[] exName={"پرس سینه هالتر","پرس بالا سینه دمبل","زیربغل قایقی","لت از جلو","پرس سرشانه دمبل","نشر جانب دمبل","جلو بازو دمبل","پشت بازو سیمکش","اسکوات هالتر","پرس پا دستگاه","ددلیفت رومانیایی","کرانچ شکم"};
    String[] exMuscle={"سینه","سینه بالایی","پشت","پشت","سرشانه","سرشانه","جلو بازو","پشت بازو","پا","پا","همسترینگ و باسن","شکم"};
    String[] exSets={"4 ست × 8–10","3 ست × 10–12","4 ست × 8–12","3 ست × 10–12","4 ست × 8–10","3 ست × 12–15","3 ست × 10–12","3 ست × 10–15","4 ست × 6–10","3 ست × 10–12","3 ست × 8–12","3 ست × 15–20"};
    String[] exTip={"سینه را باز نگه دار و میله را کنترل‌شده پایین بیاور.","کتف‌ها ثابت؛ دمبل‌ها را بدون ضربه به سمت بالا پرس کن.","کمر خنثی؛ دسته را به سمت پایین شکم بکش.","قفسه سینه بالا و آرنج‌ها رو به پایین؛ میله را پشت گردن نبر.","بدن ثابت و مسیر حرکت کنترل‌شده باشد.","آرنج‌ها کمی خم و حرکت تا هم‌سطح شانه کافی است.","آرنج کنار بدن ثابت بماند و فقط ساعد حرکت کند.","آرنج را کنار بدن نگه دار و در انتها مکث کوتاه کن.","زانو هم‌جهت پنجه؛ عمق را متناسب با فرم خود انتخاب کن.","کمر را به پشتی تکیه بده و زانو را قفل نکن.","لگن را عقب ببر و ستون فقرات را خنثی نگه دار.","گردن را نکش؛ با انقباض شکم شانه‌ها را بالا بیاور."};
    String[] supplement={"پروتئین وی","کراتین مونوهیدرات","کافئین","بتاآلانین","BCAA","گینر"};
    String[] suppDesc={"راهی راحت برای تأمین پروتئین روزانه؛ مقدار کل پروتئین مهم‌تر از زمان مصرف است.","از مکمل‌های پرمطالعه برای بهبود عملکرد تمرین و افزایش ذخایر کراتین عضله.","ممکن است هوشیاری و عملکرد تمرینی را افزایش دهد؛ به حساسیت فردی و خواب توجه کن.","ممکن است تحمل تلاش‌های شدید را بهتر کند؛ احساس گزگز موقت شایع است.","در صورت دریافت پروتئین کافی، برای بسیاری از افراد ضرورت مشخصی ندارد.","ترکیبی پرکالری برای افرادی که دریافت انرژی کافی از غذا برایشان دشوار است."};
    android.content.SharedPreferences prefs;
    Typeface bold=Typeface.create("sans-serif",Typeface.BOLD), regular=Typeface.create("sans-serif",Typeface.NORMAL);

    GymView(MainActivity c){super(c); activity=c; prefs=c.getSharedPreferences("gym",0); setLayerType(View.LAYER_TYPE_SOFTWARE,null); setFocusable(true);}
    void fill(Canvas c,int color){c.drawColor(color);}
    void rr(Canvas c,float l,float t,float r,float b,float rad,int color){p.setStyle(Paint.Style.FILL);p.setColor(color);p.clearShadowLayer();c.drawRoundRect(l,t,r,b,rad,rad,p);}
    void shadowCard(Canvas c,float l,float t,float r,float b,float rad){p.setStyle(Paint.Style.FILL);p.setColor(CARD);p.setShadowLayer(20,0,8,0x55000000);c.drawRoundRect(l,t,r,b,rad,rad,p);p.clearShadowLayer();}
    void txt(Canvas c,String s,float x,float y,float size,int color,Paint.Align align,boolean b){p.setStyle(Paint.Style.FILL);p.setColor(color);p.setTextSize(size);p.setTextAlign(align);p.setTypeface(b?bold:regular);c.drawText(s,x,y,p);}
    void rtl(Canvas c,String s,float x,float y,float size,int color){txt(c,s,x,y,size,color,Paint.Align.RIGHT,false);}
    void rtlB(Canvas c,String s,float x,float y,float size,int color){txt(c,s,x,y,size,color,Paint.Align.RIGHT,true);}
    void line(Canvas c,float x1,float y1,float x2,float y2,int color,float sw){p.setColor(color);p.setStrokeWidth(sw);p.setStyle(Paint.Style.STROKE);p.setStrokeCap(Paint.Cap.ROUND);c.drawLine(x1,y1,x2,y2,p);p.setStyle(Paint.Style.FILL);}
    @Override protected void onDraw(Canvas c){
        fill(c,BG); c.save(); c.clipRect(0,0,getWidth(),getHeight()-92);
        c.translate(0,-scroll);
        if(detail>=0) exerciseDetail(c); else switch(page){case 0:home(c);break;case 1:workouts(c);break;case 2:bodies(c);break;case 3:supplements(c);break;case 4:plans(c);break;case 5:progress(c);break;default:about(c);}
        c.restore(); bottom(c);
    }
    void top(Canvas c,String title,String sub){
        rtlB(c,title,getWidth()-24,48+scroll,26,WHITE); rtl(c,sub,getWidth()-24,75+scroll,12,MUTED);
        rr(c,22,24+scroll,58,60+scroll,18,Color.rgb(25,43,54));
        txt(c,"BM",40,49+scroll,12,ACCENT,Paint.Align.CENTER,true);
    }
    void home(Canvas c){
        top(c,"باشگاه من","راهنمای تمرین، تغذیه و پیشرفت");
        float y=105;
        p.setShader(new LinearGradient(20,y,getWidth()-20,y+175,Color.rgb(25,49,63),Color.rgb(12,25,34),Shader.TileMode.CLAMP));
        c.drawRoundRect(20,y,getWidth()-20,y+175,28,28,p);p.setShader(null);
        rtlB(c,"هر روز، قوی‌تر از دیروز",getWidth()-42,y+54,25,WHITE);
        rtl(c,"برنامه‌ات را بساز، تمرینت را ثبت کن",getWidth()-42,y+84,14,MUTED);
        rr(c,getWidth()-188,y+110,getWidth()-42,y+151,20,ACCENT);
        txt(c,"شروع تمرین",getWidth()-115,y+137,14,BG,Paint.Align.CENTER,true);
        circle(c,54,y+142,18,ORANGE); txt(c,"★",54,y+149,17,WHITE,Paint.Align.CENTER,true);
        rtlB(c,"نمای کلی امروز",getWidth()-24,315,21,WHITE);
        stat(c,20,335,(getWidth()-35)/2,425,"۰","تمرین ثبت‌شده",CYAN);
        stat(c,(getWidth()+5)/2,335,getWidth()-20,425,"۷۵","کیلو وزن",ACCENT);
        rtlB(c,"دسترسی سریع",getWidth()-24,465,21,WHITE);
        quick(c,20,485,(getWidth()-35)/2,585,"تمرین‌ها","بانک حرکات",ACCENT,"01");
        quick(c,(getWidth()+5)/2,485,getWidth()-20,585,"برنامه‌ها","برنامه هفتگی",ORANGE,"02");
        quick(c,20,600,(getWidth()-35)/2,700,"مکمل‌ها","راهنمای کاربرد",CYAN,"03");
        quick(c,(getWidth()+5)/2,600,getWidth()-20,700,"پیشرفت","ثبت تغییرات",PURPLE,"04");
        rtlB(c,"یادآوری",getWidth()-24,750,20,WHITE);
        shadowCard(c,20,770,getWidth()-20,850,22);rtlB(c,"فرم صحیح، اولویت اول",getWidth()-40,805,16,ACCENT);rtl(c,"وزنه کمتر با اجرای درست، بهتر از وزنه بیشتر با فرم بد است.",getWidth()-40,832,11,MUTED);
    }
    void stat(Canvas c,float l,float t,float r,float b,String n,String label,int ac){shadowCard(c,l,t,r,b,20);circle(c,l+30,t+31,15,ac);rtlB(c,n,r-18,t+39,26,WHITE);rtl(c,label,r-18,t+64,11,MUTED);}
    void quick(Canvas c,float l,float t,float r,float b,String a,String d,int ac,String n){shadowCard(c,l,t,r,b,20);rr(c,l+14,t+15,l+55,t+56,14,Color.rgb(27,43,54));txt(c,n,l+34,t+42,11,ac,Paint.Align.CENTER,true);rtlB(c,a,r-15,t+40,15,WHITE);rtl(c,d,r-15,t+64,10,MUTED);}
    void workouts(Canvas c){
        top(c,"کتابخانه تمرین","۱۲ حرکت کاربردی با راهنمای فرم");
        rtl(c,"انتخاب عضله",getWidth()-24,120,13,MUTED);
        String[] cats={"همه","سینه","پشت","سرشانه","بازو","پا","شکم"};float x=getWidth()-24;
        for(int i=0;i<cats.length;i++){float w=72;rr(c,x-w,138,x,177,19,i==0?ACCENT:CARD2);txt(c,cats[i],x-w/2,163,11,i==0?BG:WHITE,Paint.Align.CENTER,true);x-=w+8;}
        float y=205;
        for(int i=0;i<exName.length;i++){exerciseCard(c,i,20,y,getWidth()-20);y+=112;}
    }
    void exerciseCard(Canvas c,int i,float l,float t,float r){
        shadowCard(c,l,t,r,t+96,22);
        rr(c,l+14,t+14,l+78,t+78,18,Color.rgb(26,46,58));
        txt(c,String.format("%02d",i+1),l+46,t+52,18,ACCENT,Paint.Align.CENTER,true);
        rtlB(c,exName[i],r-20,t+32,17,WHITE); rtl(c,exMuscle[i]+"  •  "+exSets[i],r-20,t+58,11,MUTED);
        rtl(c,"مشاهده آموزش  ›",r-20,t+82,11,ACCENT);
    }
    void exerciseDetail(Canvas c){
        top(c,"آموزش حرکت","راهنمای اجرای صحیح و نکات");
        int i=detail; shadowCard(c,20,105,getWidth()-20,255,28);
        rr(c,38,123,112,197,20,Color.rgb(27,47,60));txt(c,String.format("%02d",i+1),75,169,24,ACCENT,Paint.Align.CENTER,true);
        rtlB(c,exName[i],getWidth()-38,148,23,WHITE);rtl(c,exMuscle[i],getWidth()-38,178,13,ACCENT);rtl(c,exSets[i],getWidth()-38,207,12,MUTED);
        section(c,"روش اجرا",285,exTip[i]);
        section(c,"نکات کلیدی",390,"حرکت را با دامنه‌ای انجام بده که بدون درد و با کنترل کامل قابل اجرا باشد. تنفس را منظم نگه دار و از تاب دادن بدن برای جبران تکرارها خودداری کن.");
        rr(c,20,535,getWidth()-20,590,18,ACCENT);txt(c,"افزودن به برنامه",getWidth()/2,570,15,BG,Paint.Align.CENTER,true);
        rtlB(c,"پیشنهاد شروع",getWidth()-24,635,19,WHITE);
        shadowCard(c,20,655,getWidth()-20,750,20);rtlB(c,"استراحت بین ست‌ها",getWidth()-40,690,14,WHITE);rtl(c,"۶۰ تا ۱۲۰ ثانیه • با توجه به شدت تمرین",getWidth()-40,718,11,MUTED);
    }
    void section(Canvas c,String h,float y,String d){rtlB(c,h,getWidth()-24,y,19,WHITE);shadowCard(c,20,y+18,getWidth()-20,y+104,20);rtl(c,d,getWidth()-38,y+48,12,WHITE);rtl(c,"• اجرای کنترل‌شده  • دامنه مناسب  • بدون درد",getWidth()-38,y+78,10,MUTED);}
    void bodies(Canvas c){
        top(c,"شناخت تیپ بدنی","برای آشنایی؛ نه تشخیص یا برچسب قطعی");
        info(c,20,110,getWidth()-20,230,"اکتومورف","ساختار معمولاً باریک‌تر و افزایش وزن برای برخی افراد دشوارتر است.","تغذیه کافی + تمرین مقاومتی",CYAN);
        info(c,20,250,getWidth()-20,370,"مزومورف","ساختار عضلانی‌تر در بسیاری از افراد؛ پاسخ بدن به تمرین و تغذیه فردی است.","پیشرفت تدریجی + برنامه منظم",ACCENT);
        info(c,20,390,getWidth()-20,510,"اندومورف","در برخی افراد ذخیره چربی آسان‌تر است؛ این دسته‌بندی قطعی یا پزشکی نیست.","کسری کالری در صورت نیاز + تمرین",ORANGE);
        shadowCard(c,20,545,getWidth()-20,650,22);rtlB(c,"نکته مهم",getWidth()-40,580,17,ACCENT);rtl(c,"تیپ‌های بدنی ابزار آموزشی ساده‌اند و ژنتیک،",getWidth()-40,612,12,WHITE);rtl(c,"ترکیب بدن و سبک زندگی را به‌طور کامل توضیح نمی‌دهند.",getWidth()-40,636,12,WHITE);
    }
    void info(Canvas c,float l,float t,float r,float b,String h,String d,String tip,int ac){shadowCard(c,l,t,r,b,22);circle(c,l+32,t+32,12,ac);rtlB(c,h,r-22,t+39,19,WHITE);rtl(c,d,r-22,t+72,11,MUTED);rtl(c,"تمرکز: "+tip,r-22,t+103,10,ac);}
    void supplements(Canvas c){
        top(c,"راهنمای مکمل‌ها","کاربرد، شواهد و نکات احتیاطی");
        shadowCard(c,20,105,getWidth()-20,190,20);rtlB(c,"مکمل جای غذا نیست",getWidth()-38,138,17,ACCENT);rtl(c,"اول خواب، تغذیه و برنامه تمرینی را منظم کن.",getWidth()-38,166,11,MUTED);
        float y=215;for(int i=0;i<supplement.length;i++){shadowCard(c,20,y,getWidth()-20,y+100,20);circle(c,getWidth()-48,y+31,18,i%2==0?ORANGE:CYAN);rtlB(c,supplement[i],getWidth()-78,y+34,16,WHITE);rtl(c,suppDesc[i],getWidth()-78,y+63,10,MUTED);y+=116;}
    }
    void plans(Canvas c){
        top(c,"برنامه تمرینی","سه الگوی نمونه برای شروع و تنظیم فردی");
        plan(c,20,110,getWidth()-20,240,"شروع قدرت","۳ روز در هفته","تمام بدن","مناسب آشنایی با حرکات و ساخت عادت",ACCENT);
        plan(c,20,260,getWidth()-20,390,"عضله‌سازی","۴ روز در هفته","بالاتنه / پایین‌تنه","حجم تمرین متوسط با روزهای استراحت",ORANGE);
        plan(c,20,410,getWidth()-20,540,"ترکیبی","۵ روز در هفته","تقسیم عضلات","برای افراد باتجربه‌تر با تنظیم حجم و شدت",CYAN);
        rr(c,20,575,getWidth()-20,635,20,ACCENT);txt(c,"ساخت برنامه شخصی",getWidth()/2,613,15,BG,Paint.Align.CENTER,true);
        rtl(c,"برنامه نمونه جای نسخه اختصاصی مربی یا پزشک نیست.",getWidth()-24,680,11,MUTED);
    }
    void plan(Canvas c,float l,float t,float r,float b,String h,String days,String type,String d,int ac){shadowCard(c,l,t,r,b,23);rtlB(c,h,r-22,t+34,20,WHITE);rtl(c,days+"  •  "+type,r-22,t+62,11,ac);rtl(c,d,r-22,t+91,11,MUTED);rr(c,l+18,t+24,l+26,t+70,4,ac);}
    void progress(Canvas c){
        top(c,"پیشرفت من","ثبت وزن و پیگیری منظم");
        float weight=prefs.getFloat("weight",75f);
        shadowCard(c,20,105,getWidth()-20,285,24);
        rtl(c,"وزن فعلی",getWidth()-42,145,13,MUTED);rtlB(c,String.format(Locale.US,"%.1f کیلو",weight),getWidth()-42,190,32,WHITE);
        rr(c,42,222,getWidth()-84,260,19,CARD2);float pct=Math.min(1,Math.max(0,(weight-50)/50f));rr(c,42,222,42+(getWidth()-84)*pct,260,19,ACCENT);
        rr(c,20,310,getWidth()-20,366,18,ACCENT);txt(c,"ثبت وزن امروز",getWidth()/2,346,15,BG,Paint.Align.CENTER,true);
        rtlB(c,"شاخص‌های پیگیری",getWidth()-24,415,20,WHITE);
        metric(c,20,440,(getWidth()-35)/2,530,"تمرین","۰","جلسه");
        metric(c,(getWidth()+5)/2,440,getWidth()-20,530,"هدف هفتگی","۳","جلسه");
        rtlB(c,"نمودار پیشرفت",getWidth()-24,580,20,WHITE);
        shadowCard(c,20,600,getWidth()-20,760,22);
        for(int i=0;i<6;i++){float xx=48+i*(getWidth()-96)/5;line(c,xx,720,xx,625,CARD2,2);}
        float[] yy={700,682,690,655,670,640};for(int i=0;i<5;i++)line(c,48+i*(getWidth()-96)/5,yy[i],48+(i+1)*(getWidth()-96)/5,yy[i+1],ACCENT,4);
        rtl(c,"این نمودار نمونه است؛ ثبت واقعی از وزن‌های واردشده ساخته می‌شود.",getWidth()-30,795,10,MUTED);
    }
    void metric(Canvas c,float l,float t,float r,float b,String h,String n,String u){shadowCard(c,l,t,r,b,20);rtl(c,h,r-15,t+28,11,MUTED);rtlB(c,n+" "+u,r-15,t+62,22,WHITE);}
    void about(Canvas c){
        top(c,"درباره باشگاه من","نسخه فارسی آموزش بدنسازی");
        shadowCard(c,20,110,getWidth()-20,330,26);
        rr(c,getWidth()/2-48,140,getWidth()/2+48,236,30,Color.rgb(24,43,55));txt(c,"BM",getWidth()/2,201,30,ACCENT,Paint.Align.CENTER,true);
        rtlB(c,"باشگاه من",getWidth()/2+70,274,24,WHITE);rtl(c,"آموزش ساده برای تمرین بهتر",getWidth()/2+70,302,12,MUTED);
        rtl(c,"مطالب آموزشی جایگزین پزشک، مربی یا متخصص تغذیه نیستند.",getWidth()-40,370,10,MUTED);
        shadowCard(c,20,355,getWidth()-20,480,22);rtlB(c,"سازنده",getWidth()-40,390,16,ACCENT);rtlB(c,"@apkmod",getWidth()-40,430,23,WHITE);rtl(c,"برای پیشنهاد و پشتیبانی",getWidth()-40,456,11,MUTED);
        rtlB(c,"امکانات نسخه",getWidth()-24,530,20,WHITE);
        String[] a={"کتابخانه حرکات","تیپ‌های بدنی","راهنمای مکمل‌ها","برنامه تمرینی","ثبت پیشرفت"};for(int i=0;i<a.length;i++){rtl(c,"✓  "+a[i],getWidth()-42,570+i*36,13,WHITE);}
    }
    void circle(Canvas c,float x,float y,float rad,int color){p.setStyle(Paint.Style.FILL);p.setColor(color);c.drawCircle(x,y,rad,p);}
    void circle(Canvas c,float x,float y,int rad,int color,int dummy){circle(c,x,y,(float)rad,color);}
    void bottom(Canvas c){
        float y=getHeight()-92;rr(c,0,y,getWidth(),getHeight(),0,Color.rgb(8,17,24));
        float w=getWidth()/7f;for(int i=0;i<7;i++){int col=i==page&&detail<0?ACCENT:MUTED;float cx=i*w+w/2;
            if(i==page&&detail<0)rr(c,cx-25,y+7,cx+25,y+39,16,Color.rgb(36,47,50));
            txt(c,navGlyph[i],cx,y+29,19,col,Paint.Align.CENTER,true);txt(c,nav[i],cx,y+61,9,col,Paint.Align.CENTER,true);
        }
    }
    void saveWeight(){final EditText input=new EditText(activity);input.setInputType(2|8192);input.setHint("مثلاً ۷۵.۵");input.setText(String.valueOf(prefs.getFloat("weight",75f)));input.setSelectAllOnFocus(true);
        new AlertDialog.Builder(activity).setTitle("ثبت وزن").setMessage("وزن فعلی را به کیلو وارد کن").setView(input).setNegativeButton("لغو",null).setPositiveButton("ذخیره",(d,w)->{try{float v=Float.parseFloat(input.getText().toString().replace(',','.'));prefs.edit().putFloat("weight",v).apply();invalidate();}catch(Exception e){}}).show();}
    @Override public boolean onTouchEvent(MotionEvent e){
        float y=e.getY(), x=e.getX();
        if(e.getAction()==MotionEvent.ACTION_DOWN){downY=lastY=y;moved=false;return true;}
        if(e.getAction()==MotionEvent.ACTION_MOVE){float dy=lastY-y;if(Math.abs(y-downY)>8)moved=true;scroll+=dy;lastY=y;float max=detail>=0?520:(page==0?170:page==1?900:page==3?600:page==4?180:page==5?120:80);if(scroll<0)scroll=0;if(scroll>max)scroll=max;invalidate();return true;}
        if(e.getAction()==MotionEvent.ACTION_UP&&!moved){
            if(y>getHeight()-100){int np=(int)(x/(getWidth()/7f));if(np>=0&&np<7){page=np;detail=-1;scroll=0;invalidate();}return true;}
            float sy=y+scroll;
            if(detail>=0){if(sy<90&&x<90){detail=-1;scroll=0;invalidate();}return true;}
            if(page==1&&sy>200){int idx=(int)((sy-205)/112);if(idx>=0&&idx<exName.length){detail=idx;scroll=0;invalidate();}}
            else if(page==5&&sy>300&&sy<380)saveWeight();
            else if(page==0&&sy>105&&sy<290){page=1;scroll=0;invalidate();}
            return true;
        }return true;
    }
}
