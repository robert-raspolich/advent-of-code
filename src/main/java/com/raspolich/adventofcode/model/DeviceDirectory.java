package com.raspolich.adventofcode.model;

import java.util.*;

public class DeviceDirectory {
    private static final List<DeviceDirectory> allDirectories = new ArrayList<>();

    private final String name;

    private final DeviceDirectory parentDirectory;

    private final Map<String, DeviceDirectory> subDirectories = new HashMap<>();
    private final Map<String, DeviceFile> files = new HashMap<>();

    public DeviceDirectory(String name) {
        this(name, null);
    }

    public DeviceDirectory(String name, DeviceDirectory parentDirectory) {
        this.name = name;
        this.parentDirectory = parentDirectory;
        allDirectories.add(this);
    }

    public String getName() {
        return name;
    }

    public DeviceDirectory getParentDirectory() {
        return parentDirectory;
    }

    public void addDirectory(DeviceDirectory directory) {
        this.subDirectories.putIfAbsent(directory.getName(), directory);
    }

    public DeviceDirectory getSubDirectory(String name) {
        return subDirectories.get(name);
    }

    public void addFile(DeviceFile file) {
        this.files.putIfAbsent(file.name(), file);
    }

    public int size() {
        int size = subDirectories.values().stream().map(DeviceDirectory::size).reduce(0, Integer::sum);
        size += files.values().stream().map(DeviceFile::size).reduce(0, Integer::sum);

        return size;
    }

    public static List<DeviceDirectory> allDirectories() {
        return Collections.unmodifiableList(allDirectories);
    }
}
