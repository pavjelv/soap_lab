package server.service;

import server.entity.Task;

import javax.jws.WebService;
import java.time.LocalTime;
import java.util.*;


@WebService(endpointInterface = "server.service.ScheduleService")
public class ScheduleServiceImpl implements ScheduleService {
    Map<Long, String> scheduleMap = new TreeMap<>();

    @Override
    public boolean scheduleOrder(String  order, Long date) {
        if(checkCreationPossibility(date) && TaskServiceImpl.getTaskByTaskName(order) != null) {
            scheduleMap.put(date, TaskServiceImpl.getTaskByTaskName(order).getName());
            return true;
        }
        return false;
    }

    @Override
    public String[] getAvailableHours() {
        List<String> result = new ArrayList<>();
        if(scheduleMap.isEmpty()) return new String[]{String.valueOf(LocalTime.now().toSecondOfDay())};
        Iterator<Long> iterator = scheduleMap.keySet().iterator();
        Long prevTime = iterator.next();
        Task prevTask = TaskServiceImpl.getTaskByTaskName(scheduleMap.get(prevTime));
        while(iterator.hasNext()) {
            Long currentTime = iterator.next();
            Task currentTask = TaskServiceImpl.getTaskByTaskName(scheduleMap.get(currentTime));
            if(currentTime > prevTime + prevTask.getDuration() * 60) {
                result.add(String.valueOf(prevTime + prevTask.getDuration() * 60));
            }
            prevTask = currentTask;
            prevTime = currentTime;
        }
        if(result.isEmpty()) {
            result.add(String.valueOf(LocalTime.now().toSecondOfDay()));
        }
        return result.toArray(new String[0]);
    }

    @Override
    public void removeOrder(Long orderStartTime) {
         scheduleMap.remove(orderStartTime);
    }

    @Override
    public boolean checkCreationPossibility(Long date) {
        Iterator<Long> iterator = scheduleMap.keySet().iterator();
        if(!iterator.hasNext()) return true;
        Long prevTime = iterator.next();
        Task prevTask = TaskServiceImpl.getTaskByTaskName(scheduleMap.get(prevTime));
        while(iterator.hasNext()) {
            Long currentTime = iterator.next();
            Task currentTask = TaskServiceImpl.getTaskByTaskName(scheduleMap.get(currentTime));
            if(currentTime > date && prevTime + prevTask.getDuration() * 60 < date) {
                return true;
            } else if(currentTime > date) {
                return false;
            }
            prevTask = currentTask;
            prevTime = currentTime;
        }
        return true;
    }

    @Override
    public String[] getSchedule() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<Long, String> entry : scheduleMap.entrySet()) {
            result.add(entry.getKey() + "&" +entry.getValue());
        }

        return result.toArray(new String[0]);
    }
}
