
public class TennisGame1 implements TennisGame {
    
    private TennisPlayer player1;
    private TennisPlayer player2;

    public TennisGame1(String player1Name, String player2Name) {
        this.player1 = new TennisPlayer(player1Name);
        this.player2 = new TennisPlayer(player2Name);
    }

    public void wonPoint(String playerName){
        TennisPlayer player = player1.getPlayer(playerName);
        if (player == null) {
            player = player2.getPlayer(playerName);
        }
        player.wonPoint();
    }

    public String getScore() {

        TennisScore tennisScore = new TennisScore(player1, player2);
        
        return tennisScore.getScoreTerm();
    }

    private class TennisPlayer { 
        private String name;
        private int score;

        public TennisPlayer(String name) {
            this.name = name;
            this.score = 0;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public TennisPlayer getPlayer(String playerName) {
            if (this.name.equals(playerName)) {
                return this;
            }
            return null;
        }

        public void wonPoint() {
            score++;
        }
    }

    private class TennisScore {
        private int player1Score;
        private int player2Score;

        private String scoreTerm;

        public TennisScore(TennisPlayer player1, TennisPlayer player2) {
            this.player1Score = player1.getScore();
            this.player2Score = player2.getScore();

            this.scoreTerm = "";
        }

        public String getScoreTerm(int playerScore) {
            switch (playerScore) {
                case 0:
                    return "Love";
                case 1:
                    return "Fifteen";
                case 2:
                    return "Thirty";
                case 3:
                    return "Forty";
                default:
                    return "Deuce";
            }
        }

        public String getScoreTerm() {
            if (player1Score == player2Score) {
                if (player1Score >= 3) {
                    scoreTerm = "Deuce";
                } else {
                    scoreTerm = getScoreTerm(player1Score) + "-All";
                }
            } else if (player1Score >= 4 || player2Score >= 4) {
                int scoreDifference = player1Score - player2Score;
                switch(scoreDifference) {
                    case 1:
                        scoreTerm = "Advantage " + player1.getName();
                        break;
                    case -1:
                        scoreTerm = "Advantage " + player2.getName();
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        scoreTerm = "Win for " + player1.getName();
                        break;
                    default:
                        scoreTerm = "Win for " + player2.getName();
                        break;
                }
            } else {
                scoreTerm = getScoreTerm(player1Score) + "-" + getScoreTerm(player2Score);
            }
            return scoreTerm;
        }
    }
}

