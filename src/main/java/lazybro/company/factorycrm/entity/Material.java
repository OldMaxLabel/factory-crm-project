package lazybro.company.factorycrm.entity;

public enum Material {

    STEEL("Сталь"),
    ALUMINIUM("Алюминий"),
    TITANIUM("Титан"),
    PLASTIC("Пластик");

    private String title;

    Material(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
