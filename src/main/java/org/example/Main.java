package org.example;

import org.example.decorators.Json;
import org.example.model.Address;
import org.example.model.City;
import org.example.model.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static Boolean testMethodIsClass(Object atrValue) {
        String mainPackageName = Main.class.getPackageName();
        String atrValuePackageName = atrValue.getClass().getPackageName();
        return atrValuePackageName.equals(mainPackageName) || atrValuePackageName.startsWith(mainPackageName + ".");
    }

    public static String generateTabs(int tabs) {
        return "\t".repeat(Math.max(0, tabs));
    }
    public static String jsonBuilder(Object o, int tabs) throws InvocationTargetException, IllegalAccessException {
        StringBuffer jsonString = new StringBuffer();
        char quotationMarks = '"';
        String quotationMarksValue;
        String strTabs = generateTabs(tabs);
        jsonString.append("{\n");

        for (Method method: o.getClass().getDeclaredMethods()) {
            if (method.getParameterTypes().length == 0
                    && Modifier.isPublic(method.getModifiers())
                    && method.getAnnotation(Json.class) != null
            ) {
                Object atrValue = method.invoke(o);
                String key = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);

                if (atrValue instanceof Number || atrValue instanceof List<?> || testMethodIsClass(atrValue))
                    quotationMarksValue = "";
                else
                    quotationMarksValue = String.valueOf(quotationMarks);

                if (atrValue instanceof List<?>) {
                    List<String> updateList = new ArrayList<>();
                    for (Object item: ((List<?>) atrValue).toArray()) {
                        String itemStr = item.toString();
                        if (!(item instanceof Number)) {
                            item = ('"' + itemStr + '"');
                            updateList.add(item.toString());
                        } else {
                            break;
                        }
                        atrValue = updateList;
                    }
                } else if (testMethodIsClass(atrValue)) {
                    atrValue = jsonBuilder(atrValue, ++tabs);
                    --tabs;
                }
                jsonString.append(strTabs).append(quotationMarks).append(key).append(quotationMarks)
                        .append(": ").append(quotationMarksValue).append(atrValue).append(quotationMarksValue)
                        .append(",\n");
            }
        }
        jsonString.delete(jsonString.length() - 2, jsonString.length());
        jsonString.append("\n").append(generateTabs(tabs - 1)).append("}");

        return jsonString.toString();
    }
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        Address address = new Address();
        City city = new City();
        user.setId(1);
        user.setName("Valentim");
        user.setCPF("444.444.444-44");
        user.setEmail("Valentim@email.vale");
        user.setTasks(List.of("Correr", "Trabalhar", "Cuidar do filho"));
        user.setPhones(List.of(984330374, 999907580));
        user.setAddress(address);
        user.getAddress().setNum(33);
        user.getAddress().setStreet("Caramuru");
        user.getAddress().setNeighborhood("Divina Providência");
        user.getAddress().setCity(city);
        user.getAddress().getCity().setCod(444);
        user.getAddress().getCity().setName("Santa Maria");
        user.getAddress().getCity().setState("Rio Grande do Sul");
        user.getAddress().getCity().setCountry("Brasil");

        System.out.println(jsonBuilder(user, 1));
    }
}
