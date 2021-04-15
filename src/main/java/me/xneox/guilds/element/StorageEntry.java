package me.xneox.guilds.element;

import org.bukkit.Material;

public class StorageEntry {
    private final Material[] acceptedMaterials;
    private int amount;
    private int maxAmount;

    public StorageEntry(Material[] acceptedMaterials) {
        this.acceptedMaterials = acceptedMaterials;
    }

    public Material[] getAcceptedMaterials() {
        return acceptedMaterials;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}
