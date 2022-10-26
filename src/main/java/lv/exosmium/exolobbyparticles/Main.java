package lv.exosmium.exolobbyparticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    private Logger logger;
    private FileConfiguration config;
    private BukkitScheduler bukkitScheduler;

    private Location particleStartPoint;
    private String particleType;
    private int particleCount;
    private int particleOffsetX;
    private int particleOffsetY;
    private int particleOffsetZ;
    private long particleDelay;


    @Override
    public void onEnable() {
        this.logger = Bukkit.getLogger();
        this.bukkitScheduler = Bukkit.getScheduler();

        this.saveDefaultConfig();
        config = this.getConfig();
        setupConfig();

        Runnable particleRunnable = () -> particleStartPoint.getWorld().spawnParticle(Particle.valueOf(particleType), particleStartPoint.getX(), particleStartPoint.getY(), particleStartPoint.getZ(), particleCount, particleOffsetX, particleOffsetY, particleOffsetZ);
        bukkitScheduler.scheduleSyncRepeatingTask(this, particleRunnable, 0, particleDelay);
        System.out.println("§4§nCoded by§8:§r §cExosmium§7 (vk.com/prodbyhakin)");
    }

    private void setupConfig() {
        particleStartPoint = new Location(Bukkit.getWorld(config.getString("Particles.start-point.world")), config.getDouble("Particles.start-point.x"), config.getDouble("Particles.start-point.y"), config.getDouble("Particles.start-point.z"));
        particleType = config.getString("Particles.type");
        try {
            Particle.valueOf(particleType);
        } catch (Exception exception) {
            logger.severe("Партикла +" + particleType + " не существует!");
            logger.severe("Типы партиклов: " + "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html");
            this.getPluginLoader().disablePlugin(this);
        }
        particleCount = config.getInt("Particles.count");
        particleOffsetX = config.getInt("Particles.offset.x");
        particleOffsetY = config.getInt("Particles.offset.y");
        particleOffsetZ = config.getInt("Particles.offset.z");
        particleDelay = config.getLong("Particles.delay");

    }


    @Override
    public void onDisable() { bukkitScheduler.cancelTasks(this); }
}
