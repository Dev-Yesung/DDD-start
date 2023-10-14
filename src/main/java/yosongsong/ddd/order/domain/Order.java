package yosongsong.ddd.order.domain;

public class Order {

	private OrderState state;
	private ShippingInfo shippingInfo;

	public void changeShippingInfo(ShippingInfo newShippingInfo) {
		if (!isShippingChangeable()) {
			throw new IllegalStateException("Can't change shipping in " + state);
		}
		this.shippingInfo = newShippingInfo;
	}

	private boolean isShippingChangeable() {
		return state == OrderState.PAYMENT_WAITING ||
			   state == OrderState.PREPARING;
	}
}