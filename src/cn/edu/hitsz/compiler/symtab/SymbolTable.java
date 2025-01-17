package cn.edu.hitsz.compiler.symtab;

import cn.edu.hitsz.compiler.utils.FileUtils;

import java.util.*;

/**
 * 符号表
 * <br>
 * 由于源语言比较简易, 加之 Java 中具有非常好用的通用数据结构类型, 本项目其实并不一定需要一个集中的 "符号表" 来存储源语言中的
 * <b>所有符号的所有信息</b>. 但为了切合理论课程教学, 提高实验实践技能的通用性, 我们按照一般编译器项目中符号表的设计设计了该符号表.
 * 其在代码中的作用可能并不明显, 但我们希望同学们可以借此体验符号表的设计思想.
 */
public class SymbolTable {
    Map<String, SymbolTableEntry> map = new HashMap<>();

    /**
     * 获取符号表中已有的条目
     *
     * @param text 符号的文本表示
     * @return 该符号在符号表中的条目
     * @throws RuntimeException 该符号在表中不存在
     */
    public SymbolTableEntry get(String text) {
        if(has(text)){
            return map.get(text);
        }
        else{
            throw new RuntimeException();
        }
    }

    /**
     * 在符号表中新增条目
     *
     * @param text 待加入符号表中的新符号的文本表示
     * @return 该符号在符号表中对应的新条目
     * @throws RuntimeException 该符号已在表中存在
     */
    public SymbolTableEntry add(String text) {
        if(has(text)){
            throw new RuntimeException();
        }
        else{
            SymbolTableEntry entry = new SymbolTableEntry(text);
            map.put(text, entry);
            return entry;
        }
    }

    /**
     * 判断符号表中有无条目
     *
     * @param text 待判断符号的文本表示
     * @return 该符号的条目是否位于符号表中
     */
    public boolean has(String text) {
        return map.containsKey(text);
    }

    /**
     * 获得符号表的所有条目以供 {@code dumpTable} 使用
     *
     * @return 符号表的所有条目
     */
    private Map<String, SymbolTableEntry> getAllEntries() {
        return map;
    }

    /**
     * 将符号表按格式输出
     *
     * @param path 输出文件路径
     */
    public void dumpTable(String path) {
        final var entriesInOrder = new ArrayList<>(getAllEntries().values());
        entriesInOrder.sort(Comparator.comparing(SymbolTableEntry::getText));

        final var lines = new ArrayList<String>();
        for (final var entry : entriesInOrder) {
            // null in %s will be "null"
            lines.add("(%s, %s)".formatted(entry.getText(), entry.getType()));
        }

        FileUtils.writeLines(path, lines);
    }

    /**
     * 修改符号表的标识符的类型
     */
    public void updateType(String text,SourceCodeType type) {
        if(has(text)){
            SymbolTableEntry entry = get(text);
            entry.setType(type);
            map.put(text, entry);
        }
    }

}

