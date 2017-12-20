import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MainPartOneTwo {
    
    public static void main(String[] args) {
        MainPartOneTwo mpo = new MainPartOneTwo();
        mpo.processInstructions("Day20/resources/example0");
        mpo.processInstructions("Day20/resources/example1");
        mpo.processInstructions("Day20/resources/input");
    }

    private void processInstructions(String inputFilePath) {
        try {
            List<String> rows = Files.readAllLines(Paths.get(inputFilePath));
            Particle[] particles = new Particle[rows.size()];
            for (int i = 0; i < particles.length; i++) {
                String[] allCoordinates = rows.get(i).split(">, ");
                String[] positionCoordinates = allCoordinates[0].substring(allCoordinates[0].indexOf('<') + 1).split(",");
                String[] velocityCoordinates = allCoordinates[1].substring(allCoordinates[1].indexOf('<') + 1).split(",");
                String[] accelerationCoordinates = allCoordinates[2].substring(allCoordinates[2].indexOf('<') + 1, allCoordinates[2].length() - 1).split(",");
                particles[i] = new Particle(new Coordinates(positionCoordinates), new Coordinates(velocityCoordinates), new Coordinates(accelerationCoordinates));
            }
            AtomicInteger particlesLeft = new AtomicInteger(particles.length);
            IntStream.range(0, 10000).forEach(i -> {
                // always update every particle (not optimal approach), no matter if destroyed or not,
                // in order to be able to give answer to part one and two at the same time
                Arrays.stream(particles).forEach(particle -> particle.update());
                particlesLeft.accumulateAndGet(particlesDestroyedByCollision(particles), (a, b) -> a - b);
            });
            System.out.println(inputFilePath + " :: Particle closest to position <0,0,0> is " + findParticleNearestToOrigin(particles));
            System.out.println(inputFilePath + " :: Particles left = " + particlesLeft.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private int particlesDestroyedByCollision(Particle[] particles) {
        Set<Integer> particlesDestroyed = new HashSet<>();
        for (int i = 0; i < particles.length; i++) {
            if (particles[i].destroyed) continue;
            for (int j = i + 1; j < particles.length; j++) {
                if (particles[i].collidesWith(particles[j])) {
                    particlesDestroyed.add(i);
                    particlesDestroyed.add(j);
                }
            }
        }
        particlesDestroyed.forEach(integer -> particles[integer.intValue()].destroyed = true);
        return particlesDestroyed.size();
    }

    private int findParticleNearestToOrigin(Particle[] particles) {
        int particle = -1;
        long minDistance = Long.MAX_VALUE;
        for (int i = 0; i < particles.length; i++) {
            long distance = particles[i].getManhattanDistance();
            particle = distance < minDistance ? i : particle;
            minDistance = Math.min(distance, minDistance);
        }
        return particle;
    }

    public class Coordinates {
        public long x;
        public long y;
        public long z;

        public Coordinates(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public Coordinates(String[] coordinates) {
            this(Long.parseLong(coordinates[0]), Long.parseLong(coordinates[1]), Long.parseLong(coordinates[2]));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinates that = (Coordinates) o;

            if (x != that.x) return false;
            if (y != that.y) return false;
            return z == that.z;
        }

        @Override
        public int hashCode() {
            int result = (int) (x ^ (x >>> 32));
            result = 31 * result + (int) (y ^ (y >>> 32));
            result = 31 * result + (int) (z ^ (z >>> 32));
            return result;
        }
    }

    public class Particle {

        public Coordinates position;
        public Coordinates velocity;
        public Coordinates acceleration;
        public boolean destroyed = false;

        public Particle(Coordinates position, Coordinates velocity, Coordinates acceleration) {
            this.position = position;
            this.velocity = velocity;
            this.acceleration = acceleration;
        }

        public long getManhattanDistance() {
            return Math.abs(this.position.x) + Math.abs(this.position.y) + Math.abs(this.position.z);
        }
        
        public void update() {
            this.velocity.x += this.acceleration.x;
            this.velocity.y += this.acceleration.y;
            this.velocity.z += this.acceleration.z;
            this.position.x += this.velocity.x;
            this.position.y += this.velocity.y;
            this.position.z += this.velocity.z;
        }
        
        public boolean collidesWith(Particle particle) {
            if (this.destroyed || particle.destroyed) return false;
            return this.position.equals(particle.position);
        }
    
    }

}
