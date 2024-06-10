public class Iterator {
    private Maintainer Maintainer = new Maintainer();
    private CustomerSystem CustomerSystem = new CustomerSystem();

    /**
     * empty constructor
     */
    public Iterator() {
    }

    /**
     * 這個方法用來取得維修人員的身分進而查詢與更改
     * @return
     */
    public Maintainer getMaintainer() {
        return Maintainer;
    }

    /**
     * 這個方法用來進入顧客系統，測試借還車
     * @return
     */
    public CustomerSystem getCustomerSystem() {
        return CustomerSystem;
    }
}
