package org.example;

import org.example.decorators.Json;
import org.example.model.Address;
import org.example.model.City;
import org.example.model.User;
import javax.swing.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void showJson(String json) {
        JFrame frame = new JFrame("Exibindo JSON");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea textArea = new JTextArea(json);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    public static Boolean testMethodIsClass(Object atrValue) {
        String mainPackageName = Main.class.getPackageName();
        String atrValuePackageName = atrValue.getClass().getPackageName();
        return atrValuePackageName.equals(mainPackageName) || atrValuePackageName.startsWith(mainPackageName + ".");
    }

    public static String generateTabs(int tabs) {
        return "\t".repeat(Math.max(0, tabs));
    }

    public static Object arrayConstructor(Object atrValue) {
        List<String> updateList = new ArrayList<>();
        for (Object item: ((List<?>) atrValue).toArray()) {
            String itemStr = item.toString();
            if (!(item instanceof Number || item instanceof Boolean)) {
                if (item instanceof List<?>)
                    item = arrayConstructor(item);
                else
                    item = ('"' + itemStr + '"');
            }
            updateList.add(item.toString());
        }

        return updateList;
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

                if (atrValue instanceof Number || atrValue instanceof List<?> || atrValue == null
                        || testMethodIsClass(atrValue) || atrValue instanceof Boolean)
                    quotationMarksValue = "";
                else
                    quotationMarksValue = String.valueOf(quotationMarks);

                if (atrValue instanceof List<?>) {
                    List<String> updateList = new ArrayList<>();

                    for (Object item: ((List<?>) atrValue).toArray()) {
                        String itemStr = item.toString();

                        if (!(item instanceof Number)) {
                            Object itemAux = item;

                            if (testMethodIsClass(itemAux))
                                item = jsonBuilder(itemAux, tabs + 1);
                            else if (item instanceof List<?>)
                                item = arrayConstructor(item);
                            else
                                item = ('"' + itemStr + '"');
                            updateList.add(item.toString());
                        } else {
                            break;
                        }
                        atrValue = updateList;
                    }
                } else {
                    if (atrValue != null && testMethodIsClass(atrValue)) {
                        atrValue = jsonBuilder(atrValue, ++tabs);
                        --tabs;
                    }
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
        user.getAddress().setNum(List.of(33, 23, 11));
        user.getAddress().setStreet("Caramuru");
        user.getAddress().setNeighborhood("Divina Providência");
        user.getAddress().setCity(city);
        user.getAddress().getCity().setCod(444);
        user.getAddress().getCity().setName("Santa Maria");
        user.getAddress().getCity().setState("Rio Grande do Sul");
        user.getAddress().getCity().setCountry("Brasil");
        user.getAddress().getCity().setBeaches(List.of("Praia 1", "Praia 2"));
        user.setListInList(List.of(List.of("L1E1", "L1E2"), List.of("L2E1", "L2E2")));
        user.setListInListNumber(List.of(List.of(1, 2), List.of(3, 4)));
        user.setPcd(true);

        User father = new User();

        father.setId(2);
        father.setName("Sid");
        father.setCPF("555.555.555-55");
        father.setEmail("Sid@email.vale");
        father.setTasks(List.of("Jogar", "Trabalhar", "Cuidar do Neto"));
        father.setPhones(List.of(888888888, 555555555));
        father.setAddress(address);

        User mother = new User();

        mother.setId(3);
        mother.setName("Susi");
        mother.setCPF("666.666.666-66");
        mother.setEmail("Susi@email.vale");
        mother.setTasks(List.of("Academia", "Trabalhar", "Cuidar do Neto"));
        mother.setPhones(List.of(666666666, 333333333));
        mother.setAddress(address);

        user.setFamily(List.of(father, mother));

        String json = jsonBuilder(user, 1);

        System.out.println(json);
        showJson(json);
    }
}
