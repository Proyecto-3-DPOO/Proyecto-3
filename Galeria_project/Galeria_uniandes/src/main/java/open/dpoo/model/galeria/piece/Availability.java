package main.java.open.dpoo.model.galeria.piece;

import main.java.open.dpoo.model.usuarios.Owner;

public class Availability {
    private Owner owner;
    private boolean blocked;
    private boolean sold;
    private boolean auctioned;

    public Availability(Owner owner, boolean blocked, boolean sold, boolean auctioned) {
        this.owner = owner;
        this.blocked = blocked;
        this.sold = sold;
        this.auctioned = auctioned;
    }

    @Override public String toString() {
        return "Availability{" + "owner=" + owner + ", blocked=" + blocked + ", sold=" + sold + ", auctioned=" +
                auctioned + '}';
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public boolean isAuctioned() {
        return auctioned;
    }

    public void setAuctioned(boolean auctioned) {
        this.auctioned = auctioned;
    }
}
