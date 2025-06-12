package org.minvas.dragonsofmugloar.game.logic.turn;

import org.minvas.dragonsofmugloar.game.logic.turn.rule.TurnRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TurnRuleEngineConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnRuleEngineConfiguration.class);

    @Bean
    public TurnRuleEngine turnRuleEngine(BeanFactory beanFactory,
                                         @Value("${app.logic.turn-rules}") List<String> turnRuleIds) {
        LOGGER.info("Provided list of rules: {}", turnRuleIds);
        List<TurnRule> turnRules = new ArrayList<>();
        for (String turnRuleId : turnRuleIds) {
            TurnRule turnRule = beanFactory.getBean(turnRuleId, TurnRule.class);
            turnRules.add(turnRule);
        }
        return new TurnRuleEngine(turnRules);
    }
}
