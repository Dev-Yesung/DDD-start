package yosongsong.ddd.order.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yosongsong.ddd.order.domain.member.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ORDERS")
@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Embedded
	private ShippingInfo shippingInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	public void changeShippingInfo(ShippingInfo newShippingInfo) {
		if (!isShippingChangeable()) {
			throw new IllegalStateException("Can't change shipping in " + status);
		}
		this.shippingInfo = newShippingInfo;
	}

	private boolean isShippingChangeable() {
		return status == OrderStatus.PAYMENT_WAITING ||
			   status == OrderStatus.PREPARING;
	}
}
