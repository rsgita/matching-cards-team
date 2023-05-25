import requstor.Requestor;

class Leaderboard {
    private boolean status;
    private String domain;
    private Requestor requestor;

    public Leaderboard(String domain) {
        this.domain=domain;
        this.requestor=new Requestor(this.domain);

        this.setStatus();
    }

    public void setStatus() {
        this.status= requestor.getStatus();
    }

    public boolean getStatus() {
        return this.status;
    }

    public String get(String difficulty) {
        if(!getStatus()) return null;
        return requestor.get(difficulty);
    }

    public String register(String difficulty, String name, int time) {
        if(!getStatus()) return null;
        return requestor.register(difficulty, name, time);
    }
}