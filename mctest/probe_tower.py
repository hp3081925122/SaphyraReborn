print("player", player()["x"], player()["y"], player()["z"])
print(js(
    "var Level = Java.loadClass('net.minecraft.world.level.Level');"
    "var sl = server.getLevel(Level.OVERWORLD);"
    "var hits = '';"
    "for (var cx = -8; cx <= 24; cx++) {"
    "  for (var cz = -8; cz <= 24; cz++) {"
    "    var chunk = sl.getChunk(cx, cz);"
    "    if (chunk.hasAnyStructureReferences() || !chunk.getAllStarts().isEmpty()) {"
    "      hits += cx + ',' + cz + ' starts=' + chunk.getAllStarts().size() + '\\n';"
    "    }"
    "  }"
    "}"
    "hits == '' ? 'none in -8..24' : hits"
))
