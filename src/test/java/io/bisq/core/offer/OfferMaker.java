package io.bisq.core.offer;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Maker;
import com.natpryce.makeiteasy.Property;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.with;

public class OfferMaker {

    public static final Property<Offer, Long> price = new Property<>();
    public static final Property<Offer, Long> minAmount = new Property<>();
    public static final Property<Offer, Long> amount = new Property<>();
    public static final Property<Offer, String> baseCurrencyCode = new Property<>();
    public static final Property<Offer, String> counterCurrencyCode = new Property<>();
    public static final Property<Offer, OfferPayload.Direction> direction = new Property<>();
    public static final Property<Offer, Boolean> useMarketBasedPrice = new Property<>();
    public static final Property<Offer, Double> marketPriceMargin = new Property<>();

    public static final Instantiator<Offer> Offer = lookup -> new Offer(
      new OfferPayload("",
              0L,
              null,
              null,
              lookup.valueOf(direction, OfferPayload.Direction.BUY),
              lookup.valueOf(price, 100000L),
              lookup.valueOf(marketPriceMargin, 0.0),
              lookup.valueOf(useMarketBasedPrice, false),
              lookup.valueOf(amount, 100000L),
              lookup.valueOf(minAmount, 100000L),
              lookup.valueOf(baseCurrencyCode, "BTC"),
              lookup.valueOf(counterCurrencyCode, "USD"),
              null,
              null,
              "SEPA",
              "",
              null,
              null,
              null,
              null,
              null,
              "",
              0L,
              0L,
              0L,
              false,
              0L,
              0L,
              0L,
              0L,
              false,
              false,
              0L,
              0L,
              false,
              null,
              null,
              0));

    public static final Maker<Offer> btcUsdOffer = a(Offer);
}
