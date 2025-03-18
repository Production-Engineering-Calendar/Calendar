package ro.unibuc.hello.model;


//{notificareId, eventId, tipVerificare, bool verificare?}

public class Notificare {
    
    private int notificareId;
    private int eventId;
    private String tipVerificare;
    private boolean verificare;

    public Notificare() {
    }
    
    public Notificare(int notificareId, int eventId, String tipVerificare, boolean verificare) {
        this.notificareId = notificareId;
        this.eventId = eventId;
        this.tipVerificare = tipVerificare;
        this.verificare = verificare;
    }

    public void setNotificareId(int notificareId) {
        this.notificareId = notificareId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setTipVerificare(String tipVerificare) {
        this.tipVerificare = tipVerificare;
    }

    public void setVerificare(boolean verificare) {
        this.verificare = verificare;
    }

    public int getNotificareId() {
        return notificareId;
    }

    public int getEventId() {
        return eventId;
    }


}
