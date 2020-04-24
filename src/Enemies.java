class Enemies {
    private int health,damage;
    public Enemies(int h){
        health = h;
    }
    public int getDamage(){
        return damage;
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int value){
        health = value;
    }
}
