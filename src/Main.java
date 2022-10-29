import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();
        String path = "resources"; //Путь к каталогу Games

        //В папке Games создайте несколько директорий: src, res, savegames, temp.
        String parent = path + "\\Games";
        createFolder(parent, new String[]{"src", "res", "savegames", "temp"}, log, "folder");
        //В каталоге src создайте две директории: main, test
        createFolder(parent + "\\src", new String[]{"main", "test"}, log, "folder");
        //В подкаталоге main создайте два файла: Main.java, Utils.java
        createFolder(parent + "\\src\\main", new String[]{"Main.java", "Utils.java"}, log, "file");
        //В каталог res создайте три директории: drawables, vectors, icons
        createFolder(parent + "\\res", new String[]{"drawables", "vectors", "icons"}, log, "folder");
        //В директории temp создайте файл temp.txt
        createFolder(parent + "\\temp", new String[]{"temp.txt"}, log, "file");

        //Записываем в temp.txt
        try (FileWriter writer = new FileWriter(parent + "\\temp\\temp.txt", false)) {
            writer.write(log.toString());

        } catch (IOException ex) {
            throw new RuntimeException("Не удалось записать в файл temp.txt. Ошибка: " + ex.getMessage());
        }

        log.append("Все каталоги и файлы созданы успешно");
        System.out.println(log.toString());
    }

    private static void createFolder(String parentFolder, String[] childList, StringBuilder log, String type) {
        //Индекс для вывода при ошибке
        int index = 0;
        //Основной блок
        try {
            //Проверяем родительскую папку
            File parentDir = new File(parentFolder);
            if (parentDir.exists() && parentDir.isDirectory()) {
                for (String child : childList) {
                    try {
                        //if (child.equals("src")) throw new RuntimeException("aboba");

                        //Присваиваем индекс
                        index++;
                        File childResult = new File(parentFolder + "\\" + child);

                        if (type.equals("folder")) childResult.mkdir();
                        if (type.equals("file")) childResult.createNewFile();

                        log.append((type.equals("file") ? "Файл " : "Каталог ") + child + " создан успешно\n");
                    } catch (RuntimeException ex) {
                        String error = "Не удалось создать папку " + childList[index] + ". Ошибка: " + ex.getMessage() + "\n";
                        System.out.println(error);
                        log.append(error);
                    } catch (IOException ex) {
                        String error = "Не удалось создать файл " + childList[index] + ". Ошибка: " + ex.getMessage() + "\n";
                        System.out.println(error);
                        log.append(error);
                    }
                }
            } else throw new RuntimeException("Неверно указан родительский каталог");
        } catch (RuntimeException ex) {
            String error = "Возникла системная ошибка. Ошибка: " + ex.getMessage() + "\n";
            System.out.println(error);
            log.append(error);
        }
    }
}