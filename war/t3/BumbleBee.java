import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

public class BumbleBee extends Bot {

    int turnDirection = 1; // clockwise (-1) or counterclockwise (1)
    double safeDistance = 100; // 안전 거리 설정

    public static void main(String[] args) {
        new BumbleBee().start();
    }

    BumbleBee() {
        super(BotInfo.fromFile("BumbleBee.json"));
    }

    @Override
    public void run() {
        // Set colors
        setBodyColor(Color.fromHex("999")); // lighter gray
        setTurretColor(Color.fromHex("888")); // gray
        setRadarColor(Color.fromHex("666")); // dark gray

        while (isRunning()) {
            turnLeft(5 * turnDirection); // 조금씩 회전
        }
    }

    @Override
    public void onScannedBot(ScannedBotEvent e) {
        turnToFaceTarget(e.getX(), e.getY());

        var distance = distanceTo(e.getX(), e.getY());

        // 안전 거리 유지
        if (distance > safeDistance) {
            forward(distance - safeDistance); // 안전 거리로 이동
        } else {
            back(10); // 너무 가까우면 뒤로 물러남
        }

        fire(2); // 적에게 총을 쏘기
        rescan(); // 다시 스캔
    }

    @Override
    public void onHitBot(HitBotEvent e) {
        // 부딪혔을 때 아무 작업도 하지 않음
    }

    private void turnToFaceTarget(double x, double y) {
        var bearing = bearingTo(x, y);
        if (bearing >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        turnLeft(bearing);
    }
}

