import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

//OYUNCU adli sinif karakter olan pacman icin

//Odevde ic class kullanmayin diye bir kural olmadigi icin   ve repaint fonksiyonunu
//tekrar kullanabilmek icin canavarlar ve oyuncu classini ic class olarak yazdim

// pacman karakteri de thread ile calisiyor

public class pacman extends JPanel {
    public static final String TITLE ="PacMan";
    public static Oyuncu oyuncu;
    public static Canavarlar c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
    public static Oduller o1,o2,o3,o4,o5,o6,o7,o8,o9,o10;
    public static ArrayList<Canavarlar> monsters;
    public static ArrayList<Oduller> oduller;
   static boolean BITTI=false;
    static boolean bittiMi=false;
    static boolean tamamenbittiMi=false;
    int kazandi=0;
    long bas;long bit;int puan=0;
    ReentrantLock lock = new ReentrantLock();
    public pacman(){
        Dimension dimension = new Dimension(1000,1000);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setBackground(Color.BLACK);
        oyuncu=new Oyuncu();
        monsters=new ArrayList<>();
        c1= new Canavarlar();c2=new Canavarlar(); c3= new Canavarlar();c4=new Canavarlar();c5=new Canavarlar(); c6= new Canavarlar(); c7=new Canavarlar(); c8= new Canavarlar(); c9= new Canavarlar();c10= new Canavarlar();
        monsters.add(c1);monsters.add(c2);monsters.add(c3);monsters.add(c4);monsters.add(c5);monsters.add(c6);monsters.add(c7);monsters.add(c8);monsters.add(c9);monsters.add(c10);
        boolean cik = false;
        for (int i = 0; i < 10; i++) {
            cik = false;
            int index1 = ((int) ((Math.random()) * 950));
            int index2 = ((int) ((Math.random()) * 950));
            for (int j = 0; j < monsters.size(); j++) {
                if ((index1 > 950 || index2 > 950) || (index1 >= 900 && index2 >= 900)) {
                    cik = true;
                    break;
                }
                if (i==0){
                    monsters.get(i).x = index1;
                    monsters.get(i).y = index2;
                    break;
                }
                if ((index1 > monsters.get(j).x && monsters.get(j).x + 50 > index1 && index2 > monsters.get(j).y && index2 < monsters.get(j).y + 50) || (index1 + 50 > monsters.get(j).x && monsters.get(j).x + 50 > index1 + 50 && index2 + 50 > monsters.get(j).y && index2 + 50 < monsters.get(j).y + 50) || (index1 > monsters.get(j).x && monsters.get(j).x + 50 > index1 && index2 + 50 > monsters.get(j).y && index2 + 50 < monsters.get(j).y + 50) || (index1 + 50 > monsters.get(j).x && monsters.get(j).x + 50 > index1 + 50 && index2 > monsters.get(j).y && index2 < monsters.get(j).y + 50)) {
                    cik = true;
                    break;
                }
            }
            if (cik == true) {
                i--;
                continue;
            } else {
                if (i != 0) {
                    monsters.get(i).x = index1;
                    monsters.get(i).y = index2;
                }
            }
        }
        oduller= new ArrayList<>();
        o1=new Oduller(); o2=new Oduller();o3=new Oduller(); o4=new Oduller(); o5=new Oduller();o6=new Oduller();o7=new Oduller();o8=new Oduller();o9=new Oduller();o10=new Oduller();
        oduller.add(o1);oduller.add(o2);oduller.add(o3);oduller.add(o4); oduller.add(o5);oduller.add(o6);oduller.add(o7);oduller.add(o8);oduller.add(o9);oduller.add(o10);
        for (int i=0; i<oduller.size();i++){
            cik=false;
            int index1 = ((int) ((Math.random()) * 975));
            int index2 = ((int) ((Math.random()) * 975));
            for(int j=0;j<oduller.size();j++){
                if ( (index1 >= 950 && index2 >= 950)) {
                    cik = true;
                    break;
                }
                if(((index1%50)!=0) || (index2%50!=0)){
                    cik=true;break;
                }
                if (i==0){
                    oduller.get(i).x = index1;
                    oduller.get(i).y = index2;
                    break;
                }
                if ((index1 > oduller.get(j).x && oduller.get(j).x + 25 > index1 && index2 > oduller.get(j).y && index2 < oduller.get(j).y + 25) || (index1 +25 > oduller.get(j).x && oduller.get(j).x +25 > index1 + 25 && index2 +25 > oduller.get(j).y && index2 +25< oduller.get(j).y +25) || (index1 > oduller.get(j).x && oduller.get(j).x +25> index1 && index2+25 > oduller.get(j).y && index2 +25 < oduller.get(j).y + 25) || (index1 +25> oduller.get(j).x && oduller.get(j).x + 25 > index1 + 25 && index2 > oduller.get(j).y && index2 < oduller.get(j).y + 25)) {
                    cik = true;
                    break;
                }

            }
            if (cik == true) {
                i--;
            } else {
                if (i != 0) {
                    oduller.get(i).x = index1;
                    oduller.get(i).y = index2;
                }
            }
        }
    }


    @Override
   public synchronized void paint(Graphics g){
        super.paint(g);

        g.setColor(Color.GREEN);
        for(int j=0;j<oduller.size();j++){
            if(oduller.get(j).ye(oyuncu)==true){
                oduller.get(j).x=-100;oduller.get(j).y=-100;
                puan+=5;
            }else
            g.fillRect(oduller.get(j).x+12,oduller.get(j).y+12,25,25);
        }
        g.setColor(Color.YELLOW);
        g.fillArc(oyuncu.x,oyuncu.y,50,50,oyuncu.aci1,oyuncu.aci2);
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).hiz == 0) {
                if (monsters.get(i).goThrY == true) g.setColor(new Color(102, 0, 204));
                else g.setColor(new Color(0, 0, 102));
            } else if (monsters.get(i).hiz == 1) {
                if (monsters.get(i).goThrY == true) g.setColor(new Color(255, 0, 0));
                else g.setColor(new Color(0, 128, 255));
            } else {
                if (monsters.get(i).goThrY == true) g.setColor(new Color(255, 102, 178));
                else g.setColor(new Color(51, 255, 255));
            }
                g.fillOval(monsters.get(i).x, monsters.get(i).y, 50, 50);
        }

   }

   class Oyuncu implements Runnable,KeyListener{
       int x=950;int y=950;
       int c;
       int sayac=0;int tsayac=1;
       boolean basildi;
       int aci1=200;int aci2=320;
       @Override
       public void run() {
          while (true) {
              if (basildi == true) {
                  if(sayac==tsayac) {
                      if (c == KeyEvent.VK_LEFT) {
                          if (this.x - 50 >= 0){
                              this.x -= 50;
                              aci1=200;
                          }
                      } else if (c == KeyEvent.VK_RIGHT) {
                          if (this.x + 50 <= 950) {
                              this.x += 50;
                              aci1=20;
                          }
                      } else if (c == KeyEvent.VK_DOWN) {
                          if (this.y + 50 <= 950) {
                              this.y += 50;
                              aci1=280;
                              aci2=320;
                          }
                      } else if (c == KeyEvent.VK_UP) {
                          if (this.y - 50 >= 0) {
                              this.y -= 50;
                              aci1=120;
                              aci2=300;
                          }
                      }
                      tsayac++;

                  }

              }
              repaint();
              if(pacmanKontrol(this)==true) break;
          }
       }

       @Override
       public void keyTyped(KeyEvent keyEvent) { }

       @Override
       public void keyPressed(KeyEvent keyEvent) {
           c = keyEvent.getKeyCode();
         //  if(pacmanKontrol(oyuncu)==false)
           //repaint();
           basildi=true;
           sayac++;
       }

       @Override
       public synchronized void keyReleased(KeyEvent keyEvent) { basildi=false; }
   }

   public  void repaint(){
        super.repaint();
   }

   public void threatStart(){
        bas=System.currentTimeMillis();
        Thread pacman= new Thread(oyuncu);
        pacman.start();
        for(int i=0;i<monsters.size();i++){
            Thread thread =new Thread(monsters.get(i));
            thread.start();

        }
   }

    class EndingListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            System.exit(0);
        }
    }

    public void yonDegistir(Canavarlar c){
        boolean cik=false;
        for(int j=0; j<monsters.size();j++){
            if(monsters.get(j)==c) continue;
            if ((c.x > monsters.get(j).x && monsters.get(j).x + 50 > c.x && c.y > monsters.get(j).y && c.y < monsters.get(j).y + 50) || (c.x + 50 > monsters.get(j).x && monsters.get(j).x + 50 > c.x + 50 && c.y + 50 > monsters.get(j).y && c.y + 50 < monsters.get(j).y + 50) || (c.x > monsters.get(j).x && monsters.get(j).x + 50 > c.x && c.y + 50 > monsters.get(j).y && c.y + 50 < monsters.get(j).y + 50) || (c.x + 50 > monsters.get(j).x && monsters.get(j).x + 50 > c.x + 50 && c.y > monsters.get(j).y && c.y < monsters.get(j).y + 50)) {
                cik = true;
                break;
            }
        }
        if(cik==true){
            if(c.goThrY==true){
                if(c.toDown==true) {
                    c.toDown=false;
                    c.y-=50;
                }
                else {
                    c.toDown=true;
                    c.y+=50;
                }
            }
            else{
                if(c.toRight==true){
                    c.toRight=false;
                    c.x-=50;
                }
                else{
                    c.toRight=true;
                    c.x+=50;
                }
            }

        }
    }


    public boolean pacmanKontrol(Oyuncu c){
        boolean cik=false;
        for(int j=0; j<monsters.size();j++){
            if ((c.x > monsters.get(j).x && monsters.get(j).x + 50 > c.x && c.y > monsters.get(j).y && c.y < monsters.get(j).y + 50) || (c.x + 50 > monsters.get(j).x && monsters.get(j).x + 50 > c.x + 50 && c.y + 50 > monsters.get(j).y && c.y + 50 < monsters.get(j).y + 50) || (c.x > monsters.get(j).x && monsters.get(j).x + 50 > c.x && c.y + 50 > monsters.get(j).y && c.y + 50 < monsters.get(j).y + 50) || (c.x + 50 > monsters.get(j).x && monsters.get(j).x + 50 > c.x + 50 && c.y > monsters.get(j).y && c.y < monsters.get(j).y + 50)) {
                cik = true;
                break;
            }
        }
        if(oyuncu.x==0 && oyuncu.y==0) {
            cik=true;
            kazandi=1;
        }

        if(cik==true){
            repaint();
          return true;
        }
        return false;
    }


    class Canavarlar implements Runnable {
        int x = -1;
        int y = -1;
        boolean goThrY;
        boolean toRight = true;
        boolean toDown = true;
        int hiz;
        int yon;
        public Canavarlar() {
            hiz = (int) (Math.random() * 3);
            yon= (int)(Math.random()*2);
            if(yon==0) goThrY=true;
            else goThrY=false;
        }

        @Override
        public synchronized void run() {
            while (BITTI==false && bittiMi==false && tamamenbittiMi==false) {
            if (this.goThrY == true) {
                if (this.toDown == false) {
                    if (this.hiz == 0) {//yukaricik

                        try {
                            Thread.sleep(4000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.y-50>=0){
                            this.y -= 50;
                        }
                        else{
                            this.y+=50;
                            this.toDown=true;
                        }

                    }

                    else if (this.hiz == 1) {//yukaricik
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.y-50>=0){
                            this.y -= 50;

                        }
                        else{
                            this.y+=50;
                            this.toDown=true;

                        }

                    }
                    else {//yukaricik
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.y-50>=0){
                            this.y -=50;

                        }
                        else{
                            this.y+=50;
                            this.toDown=true;

                        }
                    }
                    if(BITTI==true) break;
                    yonDegistir(this); repaint();
                    bittiMi=pacmanKontrol(oyuncu);  if(bittiMi==true) break;
                }
                else {//asagiin

                    if (this.hiz == 0) {//asagiin
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.y+50<=950) {
                            this.y += 50;
                        }
                        else{
                            this.y-=50;
                            this.toDown=false;
                        }
                    }
                    else if (this.hiz == 1) {//asagiin

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.y+50<=950) {
                            this.y += 50;
                        }
                        else{
                            this.y-=50;
                            this.toDown=false;
                        }
                    }
                    else {//asagiin
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.y+50<=950) {
                            this.y += 50;
                        }
                        else{
                            this.y-=50;
                            this.toDown=false;
                        }
                    }
                    if(BITTI==true) break;
                    yonDegistir(this); repaint();
                    bittiMi=pacmanKontrol(oyuncu);  if(bittiMi==true) break;
                }
                repaint();
            } else {
                if (this.toRight == true) {//saga
                    if (this.hiz == 0) {//sagagit
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.x+50<=950) {
                            this.x += 50;
                        }
                        else {
                            this.x-=50;
                            this.toRight=false;
                        }
                    }
                    else if (this.hiz == 1) {//sagagit
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.x+50<=950) {
                            this.x += 50;
                        }
                        else {
                            this.x-=50;
                            this.toRight=false;
                        }

                    }
                    else {//sagagit

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.x+50<=950) {
                            this.x += 50;
                        }
                        else {
                            this.x-=50;
                            this.toRight=false;
                        }
                    }
                    if(BITTI==true) break;
               yonDegistir(this); repaint();
                    bittiMi=pacmanKontrol(oyuncu); if(bittiMi==true) break;
                } else {
                    if (this.hiz == 0) {//solagit

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.x-50>=0) {
                            this.x -= 50;
                        }
                        else{
                            this.x+=50;
                            this.toRight=true;
                        }
                    }
                    else if (this.hiz == 1) {//solagit
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.x-50>=0) {
                            this.x -= 50;
                        }
                        else{
                            this.x+=50;
                            this.toRight=true;
                        }
                    }
                    else {//solagit
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(this.x-50>=0) {
                            this.x -=50;
                        }
                        else{
                            this.x+=50;
                            this.toRight=true;
                        }

                    }
                    if(BITTI==true) break;
                 yonDegistir(this); repaint();
                    bittiMi=pacmanKontrol(oyuncu); if(bittiMi==true) break;
                }
                repaint();
            }

            }
            lock.lock();
            if((bittiMi==true || BITTI==true)&& tamamenbittiMi==false){

                bit=System.currentTimeMillis();
                puan+=(int)((bit-bas)/1000);
                tamamenbittiMi=true;
                BITTI=true;
                JFrame  popup = new JFrame("Oyun Bitti");
                JPanel p = new JPanel(new GridLayout(3,1)); popup.add(p);
                JButton tamam = new JButton("Tamam");tamam.addActionListener(new EndingListener());
                JLabel puanx= new JLabel("                         PUANINIZ: "+puan+"");
                popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                popup.setSize(300,200);
                popup.setVisible(true);
                popup.setResizable(false);
                if(kazandi==1){
                    JLabel label1= new JLabel("                     OYUNU KAZANDINIZ!");
                    p.add(label1);
                }
                else{
                    JLabel label2=new JLabel("                      OYUNU KAYBETTINIZ");
                    p.add(label2);
                }
                p.add(puanx);
                p.add(tamam);

            }
            lock.unlock();

        }
    }
    public static void main(String [] args){
        pacman oyun = new pacman();
        JFrame ekran = new JFrame();
        ekran.setTitle(oyun.TITLE);
        ekran.setResizable(false);
        ekran.addKeyListener(oyuncu);
        ekran.add(oyun);
        ekran.pack();
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ekran.setVisible(true);
        oyun.threatStart();

    }

}
