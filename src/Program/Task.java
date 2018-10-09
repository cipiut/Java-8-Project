package Program;

import Program.MonitoredData;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task {

    public LinkedList<MonitoredData> read() throws Exception {
        return Files.lines(Paths.get("/home/catalin/IdeaProjects/Tema5/src/Activities.txt"))
                .map(w -> w.split("\t\t"))
                .map(w -> new MonitoredData(w[0], w[1], w[2]))
                .collect(Collectors.toCollection(LinkedList<MonitoredData>::new));
    }

    public void writeTask2(List<MonitoredData> monitor) throws Exception {
        Files.write(Paths.get("/home/catalin/IdeaProjects/Tema5/src/OutputTask2.txt"), () ->
                map(monitor).entrySet()
                        .stream()
                        .<CharSequence>map(e -> e.getKey() + ": " + e.getValue())
                        .iterator());
    }

    public void writeTask3(List<MonitoredData> monitor) throws Exception {
        Files.write(Paths.get("/home/catalin/IdeaProjects/Tema5/src/OutputTask3.txt"), () ->
                filterByHour(monitor).entrySet()
                        .stream()
                        .<CharSequence>map(e -> e.getKey() + ": " + e.getValue().toHours() + " hours")
                        .iterator());
    }

    public void writeTask4(List<MonitoredData> monitor) throws Exception {
        Files.write(Paths.get("/home/catalin/IdeaProjects/Tema5/src/OutputTask4.txt"), () ->
                mapEachDay(monitor).entrySet()
                        .stream()
                        .<CharSequence>map(e -> "Day " + e.getKey() + ":\n" + e.getValue())
                        .iterator());
    }

    public void writeTask5(List<MonitoredData> monitor) throws Exception {
        Files.write(Paths.get("/home/catalin/IdeaProjects/Tema5/src/OutputTask5.txt"), () ->
                filterByMinutes(monitor)
                        .stream()
                        .<CharSequence>map(e -> e).iterator());
    }

    public long count(List<MonitoredData> monitor) {
        return Stream.concat(monitor.stream().map(w -> w.getStartTime().substring(0, 10)),
                monitor.stream().map(w -> w.getEndTime().substring(0, 10))).distinct().count();
    }

    public Map<String, Integer> map(List<MonitoredData> monitor) {
        return monitor.stream().collect(
                Collectors.groupingBy(MonitoredData::getActivity,
                        Collectors.collectingAndThen(
                                Collectors.mapping(MonitoredData::getActivity, Collectors.toList()), List::size)));
    }

    public Map<Integer, Map<String, Integer>> mapEachDay(List<MonitoredData> monitor) {
        return monitor.stream()
                .collect(Collectors.groupingBy(f -> Integer.valueOf(f.getStartTime().substring(8, 10)),
                        Collectors.groupingBy(MonitoredData::getActivity,
                                Collectors.collectingAndThen(
                                        Collectors.mapping(MonitoredData::getActivity,
                                                Collectors.toList()), List::size))));
    }

    private Map<String, Duration> period(List<MonitoredData> monitor) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return monitor.stream().collect(
                Collectors.groupingBy(MonitoredData::getActivity,
                        Collectors.reducing(Duration.ZERO,
                                f -> Duration.between(LocalDateTime.parse(f.getStartTime(), format),
                                        LocalDateTime.parse(f.getEndTime(), format)).abs(), Duration::plus
                        )
                ));
    }

    public Map<String, Duration> filterByHour(List<MonitoredData> c) {
        return period(c).entrySet().stream().filter(w -> w.getValue().toHours() >= 10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, List<Long>> periodEach(List<MonitoredData> monitor) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return monitor.stream().collect(
                Collectors.groupingBy(MonitoredData::getActivity,
                        Collectors.mapping(f -> Duration.between(LocalDateTime.parse(f.getStartTime(), format),
                                LocalDateTime.parse(f.getEndTime(), format)).toMinutes(), Collectors.toList())));
    }

    public List<String> filterByMinutes(List<MonitoredData> c) {
        return periodEach(c).entrySet()
                .stream()
                .filter(x -> ((x.getValue().stream().filter(g -> g < 5).count() * 100) / (1. * x.getValue().size())) >= 90)
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedList<String>::new));
    }

}