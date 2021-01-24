public class Oduller  {
    int x;
    int y;



    public boolean ye(pacman.Oyuncu o){
        if(this.x+12>o.x && this.y+12>o.y&& this.x+12+25<o.x+50 && this.y+12+25<o.y+50) return true;

        return false;
    }
}
