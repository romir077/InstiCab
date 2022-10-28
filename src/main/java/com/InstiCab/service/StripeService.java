package com.InstiCab.service;

import com.InstiCab.models.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public interface StripeService  {
    Charge charge(ChargeRequest chargeRequest) throws StripeException;
}