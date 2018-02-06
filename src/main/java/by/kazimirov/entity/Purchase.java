package by.kazimirov.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class Purchase  implements IDatabaseEntity {
    private int purchaseId;
    private int accountId;
    private List<Integer> tracksId;
    private LocalDateTime date;
    private double totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return purchaseId == purchase.purchaseId &&
                accountId == purchase.accountId &&
                Double.compare(purchase.totalPrice, totalPrice) == 0 &&
                Objects.equals(tracksId, purchase.tracksId) &&
                Objects.equals(date, purchase.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, accountId, tracksId, date, totalPrice);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Purchase{");
        sb.append("purchaseId=").append(purchaseId);
        sb.append(", accountId=").append(accountId);
        sb.append(", tracksId=").append(tracksId);
        sb.append(", date=").append(date);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append('}');
        return sb.toString();
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public List<Integer> getTracksId() {
        return tracksId;
    }

    public void setTracksId(List<Integer> tracksId) {
        this.tracksId = tracksId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
