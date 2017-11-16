package com.adagio.java8.lesson07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 处理文件
 *
 */
public class FilesDemo {

	public static void main(String[] args) throws Exception {
		//列出文件
		try (Stream<Path> stream = Files.list(Paths.get(""))) {
		    String joined = stream
		        .map(String::valueOf)
		        .filter(path -> !path.startsWith("."))
		        .sorted()
		        .collect(Collectors.joining("; "));
		    System.out.println("List: " + joined);
		}
		
		//查找文件
		//find方法接受三个参数：目录路径start是起始点，
		//maxDepth定义了最大搜索深度。
		//第三个参数是一个匹配谓词，定义了搜索的逻辑
		Path start = Paths.get("");
		int maxDepth = 5;
		try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
		        String.valueOf(path).endsWith(".js"))) {
		    String joined = stream
		        .sorted()
		        .map(String::valueOf)
		        .collect(Collectors.joining("; "));
		    System.out.println("Found: " + joined);
		}
		
		//Files.walk会遍历每个文件，而不需要传递搜索谓词
		try (Stream<Path> stream = Files.walk(start, maxDepth)) {
		    String joined = stream
		        .map(String::valueOf)
		        .filter(path -> path.endsWith(".js"))
		        .sorted()
		        .collect(Collectors.joining("; "));
		    System.out.println("walk(): " + joined);
		}
		
		//读写文件
		List<String> lines = Files.readAllLines(Paths.get("resource\\java8\\lesson03\\demo1.js"));
		lines.add("print('foobar');");
		Files.write(Paths.get("resource/nashorn1-modified.js"), lines);
		
		
		//使用Files.lines方法来作为内存高效的替代
		try (Stream<String> stream = Files.lines(Paths.get("resource/nashorn1-modified.js"))) {
		    stream
		        .filter(line -> line.contains("print"))
		        .map(String::trim)
		        .forEach(System.out::println);
		}
		
		
		//精细控制
		
		//读取文件
		Path path = Paths.get("resource/nashorn1-modified.js");
		try (BufferedReader reader = Files.newBufferedReader(path)) {
		    System.out.println(reader.readLine());
		}
		
		//写入文件
		Path path1 = Paths.get("resource/output.js");
		try (BufferedWriter writer = Files.newBufferedWriter(path1)) {
		    writer.write("print('Hello World');");
		}
		
		
		Path path2 = Paths.get("resource/output.js");
		try (BufferedReader reader = Files.newBufferedReader(path2)) {
		    long countPrints = reader
		        .lines()
		        .filter(line -> line.contains("print"))
		        .count();
		    System.out.println(countPrints);
		}
	}
}
