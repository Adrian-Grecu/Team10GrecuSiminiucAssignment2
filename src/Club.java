public class Club {
    private final int normalCustomersTotal = 15;
    private final int vipTotal=5;
    public static void main(String[] args) {
        Club club = new Club();
        club.startup();
    }
    public void startup() {
        NormalCustomer[] normalCustomers = new NormalCustomer[normalCustomersTotal];
        Vip[] vips=new Vip[vipTotal];
        DancingFloor dancingFloor = new DancingFloor();
        for (int i = 0; i < normalCustomersTotal; i++) {
            normalCustomers[i] = new NormalCustomer(dancingFloor, "NormalCustomer " + i );
            normalCustomers[i].start();
        }
        for (int i = 0; i < vipTotal; i++) {
            vips[i] = new Vip(dancingFloor, "Vip " + i );
            vips[i].start();
        }
    }
}
