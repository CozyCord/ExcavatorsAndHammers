import gg.meza.stonecraft.mod

plugins {
    id("gg.meza.stonecraft")
}

modSettings {
}

repositories {
    mavenCentral()
}

dependencies {
    if (mod.isFabric) {
        modImplementation("net.fabricmc.fabric-api:fabric-api:${mod.prop("fabric_version")}")
    }
}

sourceSets {
    main {
        resources {
            srcDir("src/main/resources")
        }
    }
}
