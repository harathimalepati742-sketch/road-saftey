package com.roadsafe.game;

import com.roadsafe.game.entity.GameOption;
import com.roadsafe.game.entity.Scenario;
import com.roadsafe.game.repository.ScenarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedScenarios(ScenarioRepository repository) {
        return args -> {
            if (repository.count() > 0) return;

            repository.saveAll(List.of(
                new Scenario(
                    "Traffic Signal",
                    "You are riding your bicycle and the traffic signal changes to RED. What should you do?",
                    "🚦",
                    "Red means STOP. Always wait until the signal becomes green.",
                    List.of(
                        new GameOption("🛑 Stop and wait", true),
                        new GameOption("🚴 Keep going", false),
                        new GameOption("🏎️ Speed up", false)
                    )
                ),
                new Scenario(
                    "Pedestrian Crossing",
                    "You want to cross a busy road. What is the safest choice?",
                    "🚸",
                    "Always use a zebra crossing and check for vehicles before crossing.",
                    List.of(
                        new GameOption("🏃 Run anywhere", false),
                        new GameOption("🚸 Use the zebra crossing", true),
                        new GameOption("📱 Look at your phone", false)
                    )
                ),
                new Scenario(
                    "Helmet Safety",
                    "You are going to ride a motorcycle. What should you do first?",
                    "🪖",
                    "A properly fitted helmet protects your head and can save your life.",
                    List.of(
                        new GameOption("🪖 Wear a helmet", true),
                        new GameOption("📱 Use your phone", false),
                        new GameOption("🏍️ Start riding immediately", false)
                    )
                ),
                new Scenario(
                    "School Zone",
                    "You are driving near a school. What should you do?",
                    "🏫",
                    "Slow down near schools because children may suddenly cross the road.",
                    List.of(
                        new GameOption("🏎️ Drive faster", false),
                        new GameOption("🐢 Slow down", true),
                        new GameOption("📱 Use your phone", false)
                    )
                ),
                new Scenario(
                    "Emergency Vehicle",
                    "An ambulance with its siren on is coming behind you. What should you do?",
                    "🚑",
                    "Emergency vehicles need a clear path. Safely give way to them.",
                    List.of(
                        new GameOption("🚗 Block the ambulance", false),
                        new GameOption("↔️ Give way safely", true),
                        new GameOption("🏎️ Race the ambulance", false)
                    )
                )
            ));
        };
    }
}
