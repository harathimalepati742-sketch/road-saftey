import { useEffect, useState } from "react";
import "./App.css";

const API = "http://localhost:8080/api";

const fallbackScenarios = [
  {
    id: 1,
    title: "Traffic Signal",
    emoji: "🚦",
    situation: "You are riding your bicycle and the traffic signal changes to RED. What should you do?",
    options: [
      { text: "🛑 Stop and wait", correct: true },
      { text: "🚴 Keep going", correct: false },
      { text: "🏎️ Speed up", correct: false }
    ],
    explanation: "Red means STOP. Always wait until the signal becomes green."
  },
  {
    id: 2,
    title: "Pedestrian Crossing",
    emoji: "🚸",
    situation: "You want to cross a busy road. What is the safest choice?",
    options: [
      { text: "🏃 Run anywhere", correct: false },
      { text: "🚸 Use the zebra crossing", correct: true },
      { text: "📱 Look at your phone", correct: false }
    ],
    explanation: "Always use a zebra crossing and check for vehicles before crossing."
  },
  {
    id: 3,
    title: "Helmet Safety",
    emoji: "🪖",
    situation: "You are going to ride a motorcycle. What should you do first?",
    options: [
      { text: "🪖 Wear a helmet", correct: true },
      { text: "📱 Use your phone", correct: false },
      { text: "🏍️ Start riding immediately", correct: false }
    ],
    explanation: "A properly fitted helmet protects your head and can save your life."
  },
  {
    id: 4,
    title: "School Zone",
    emoji: "🏫",
    situation: "You are driving near a school. What should you do?",
    options: [
      { text: "🏎️ Drive faster", correct: false },
      { text: "🐢 Slow down", correct: true },
      { text: "📱 Use your phone", correct: false }
    ],
    explanation: "Slow down near schools because children may suddenly cross the road."
  },
  {
    id: 5,
    title: "Emergency Vehicle",
    emoji: "🚑",
    situation: "An ambulance with its siren on is coming behind you. What should you do?",
    options: [
      { text: "🚗 Block the ambulance", correct: false },
      { text: "↔️ Give way safely", correct: true },
      { text: "🏎️ Race the ambulance", correct: false }
    ],
    explanation: "Emergency vehicles need a clear path. Safely give way to them."
  }
];

function App() {
  const [screen, setScreen] = useState("home");
  const [playerName, setPlayerName] = useState("");
  const [playerId, setPlayerId] = useState(null);
  const [scenarios, setScenarios] = useState(fallbackScenarios);
  const [current, setCurrent] = useState(0);
  const [score, setScore] = useState(0);
  const [lives, setLives] = useState(3);
  const [feedback, setFeedback] = useState(null);
  const [leaderboard, setLeaderboard] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch(`${API}/scenarios`)
      .then(r => r.ok ? r.json() : Promise.reject())
      .then(data => { if (Array.isArray(data) && data.length) setScenarios(data); })
      .catch(() => {});
  }, []);

  const startGame = async () => {
    if (!playerName.trim()) {
      setError("Please enter your name.");
      return;
    }
    setError("");
    setLoading(true);
    try {
      const res = await fetch(`${API}/players`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name: playerName.trim() })
      });
      if (res.ok) {
        const player = await res.json();
        setPlayerId(player.id);
      }
    } catch (_) {
      // Frontend can still run in demo mode if backend is unavailable.
    } finally {
      setLoading(false);
      setCurrent(0);
      setScore(0);
      setLives(3);
      setFeedback(null);
      setScreen("game");
    }
  };

  const answer = async (option) => {
    if (feedback) return;

    const scenario = scenarios[current];
    const correct = Boolean(option.correct);
    const points = correct ? 10 : -5;
    const newScore = Math.max(0, score + points);
    const newLives = correct ? lives : lives - 1;

    setScore(newScore);
    setLives(newLives);
    setFeedback({
      correct,
      points,
      message: correct ? "Excellent! 🎉" : "Oops! That is unsafe. ⚠️",
      explanation: scenario.explanation
    });

    if (playerId) {
      try {
        await fetch(`${API}/game/answer`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            playerId,
            scenarioId: scenario.id,
            selectedAnswer: option.text,
            correct
          })
        });
      } catch (_) {}
    }
  };

  const next = () => {
    if (lives <= 0 || current >= scenarios.length - 1) {
      finishGame();
      return;
    }
    setCurrent(v => v + 1);
    setFeedback(null);
  };

  const finishGame = async () => {
    setScreen("result");
    try {
      const res = await fetch(`${API}/leaderboard`);
      if (res.ok) setLeaderboard(await res.json());
    } catch (_) {}
  };

  const restart = () => {
    setCurrent(0);
    setScore(0);
    setLives(3);
    setFeedback(null);
    setScreen("home");
  };

  const progress = ((current + 1) / scenarios.length) * 100;
  const scenario = scenarios[current];

  if (screen === "home") {
    return (
      <div className="app home">
        <div className="hero">
          <div className="hero-badge">🚦 HACKATHON EDITION</div>
          <h1>RoadSafe <span>Kids</span></h1>
          <p>Learn traffic rules through an exciting interactive game!</p>

          <div className="hero-road">
            <span>🚶</span><span>🚲</span><span>🚗</span><span>🚌</span><span>🚑</span>
          </div>

          <div className="start-card">
            <h2>Ready to become a Road Safety Champion?</h2>
            <input
              value={playerName}
              onChange={e => setPlayerName(e.target.value)}
              placeholder="Enter your name"
              maxLength={40}
            />
            {error && <div className="error">{error}</div>}
            <button onClick={startGame} disabled={loading}>
              {loading ? "Starting..." : "🚀 Start Game"}
            </button>
            <div className="mini-features">
              <span>⭐ +10 Correct</span>
              <span>❌ -5 Wrong</span>
              <span>❤️ 3 Lives</span>
            </div>
          </div>
        </div>
      </div>
    );
  }

  if (screen === "result") {
    return (
      <div className="app result-page">
        <div className="result-card">
          <div className="trophy">🏆</div>
          <h1>Road Safety Champion!</h1>
          <p className="small">Final Score</p>
          <div className="score-big">{score}</div>
          <p>{playerName || "Player"}, keep following safe road practices!</p>
          <div className="result-message">
            {score >= 40 ? "🌟 Amazing! You are a traffic-rule expert!" : "💪 Good try! Practice more and stay safe!"}
          </div>
          <button onClick={restart}>🔄 Play Again</button>

          {leaderboard.length > 0 && (
            <div className="leaderboard">
              <h2>🏆 Leaderboard</h2>
              {leaderboard.slice(0, 5).map((p, i) => (
                <div className="rank" key={p.id || i}>
                  <b>#{i + 1}</b><span>{p.name}</span><strong>{p.score} ⭐</strong>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    );
  }

  return (
    <div className="app">
      <header className="topbar">
        <div>
          <h1>🚦 RoadSafe Kids</h1>
          <small>Learn • Play • Stay Safe</small>
        </div>
        <div className="score">⭐ {score}</div>
      </header>

      <div className="road">
        <div className="lane-line"></div>
        <div className="moving car">🚗</div>
        <div className="moving bus">🚌</div>
        <div className="traffic-light">🔴<br/>🟡<br/>🟢</div>
      </div>

      <main className="game">
        <div className="game-info">
          <b>Level {current + 1} / {scenarios.length}</b>
          <span>{"❤️".repeat(Math.max(lives, 0))}{"🖤".repeat(3 - Math.max(lives, 0))}</span>
        </div>
        <div className="progress"><div style={{width: `${progress}%`}}></div></div>

        <section className="card">
          <div className="scenario-icon">{scenario.emoji}</div>
          <div className="tag">SAFETY SCENARIO</div>
          <h2>{scenario.title}</h2>
          <p className="question">{scenario.situation}</p>

          <div className="options">
            {scenario.options.map((option, i) => (
              <button key={i} disabled={!!feedback} onClick={() => answer(option)}>
                {option.text}
              </button>
            ))}
          </div>

          {feedback && (
            <div className={`feedback ${feedback.correct ? "correct" : "wrong"}`}>
              <h3>{feedback.message}</h3>
              <p>{feedback.explanation}</p>
              <strong>{feedback.points > 0 ? "+" : ""}{feedback.points} ⭐</strong>
              <button className="next" onClick={next}>
                {current === scenarios.length - 1 || lives <= 0 ? "Finish 🏆" : "Next Scenario ➜"}
              </button>
            </div>
          )}
        </section>
      </main>

      <footer>🚸 Learn today. Stay safe tomorrow. 🚸</footer>
    </div>
  );
}

export default App;
