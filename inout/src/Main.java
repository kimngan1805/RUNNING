import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader in=null;
        FileWriter out=null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số lượng học viên: ");
        int n = scanner.nextInt();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println("Nhập thông tin học viên thứ " + (i + 1) + ":");
            System.out.println("Nhập mã sinh viên:");
            String maSV = scanner.next();
            System.out.println("Nhập họ tên sinh viên:");
            String hoTen = scanner.next();
            System.out.println("Nhập giới tính sinh viên:");
            String gioiTinh = scanner.next();
            System.out.println("Nhập điểm python :");
            int diemPython = scanner.nextInt();
            System.out.println("Nhập điểm  java :");
            int diemJava = scanner.nextInt();
            students.add(new Student(maSV, hoTen, gioiTinh, diemPython, diemJava));
        }
        Collections.sort(students, Collections.reverseOrder());
        System.out.println("\nDanh sách học viên:");
        // in danh sach vao input
        for (Student student : students) {
            System.out.println(student);
        }
        try (PrintWriter writer= new PrintWriter("/Users/kimngan/Documents/java/inout/src/input.txt")){
            for( Student student: students){
                writer.println(student.toString());
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.print("\nNhập họ tên học viên cần tìm: ");
        String hoTenTiem = scanner.next();
        List<Student> ketQuaTimKiem = students.stream()
                .filter(student -> student.getHoTen().equals(hoTenTiem))
                .collect(Collectors.toList());
        if (ketQuaTimKiem.isEmpty()) {
            System.out.println("Không tìm thấy học viên có họ tên " + hoTenTiem);
        } else {
            System.out.println("Kết quả tìm kiếm:");
            for (Student student : ketQuaTimKiem) {
                System.out.println(student);
            }
        }
        // sắp xếp điểm trung bình giảm dần
        List<Student> studentsfile= new ArrayList<>();
        studentsfile.sort(Comparator.comparingDouble(Student:: getDiemTB).reversed());
        System.out.println("\nDanh sách học viên đậu:");
        for (Student student : students) {
            if (student.getDiemTB() >= 5) {
                System.out.println(student);
            }
        }
        // danh sach sinh vien truot
        System.out.println("Danh sách sinh viên đâu:");
        for(Student student:studentsfile){
            if(student.CheckResult().equals("Đậu")){
                System.out.println(student);
            }
        }
        //danh sach sinh vien truot
        System.out.println("Danh sach sinh vien truot:");
        for(Student student:studentsfile){
            if(student.CheckResult().equals("Trượt")){
                System.out.println(student);
            }
        }
        //danh sach hoc vien dtb treb 8:
        System.out.println("Danh sach sinh vien diem tb tren 8:");
        for(Student student:studentsfile){
            if(student.getDiemTB()>8){
                System.out.println(student);
            }
        }
        try  {
            in= new FileReader("/Users/kimngan/Documents/java/inout/src/input.txt");
            out= new FileWriter("/Users/kimngan/Documents/java/inout/src/output.txt");
            for (Student student : students) {
                out.write(student.toString() + "\n");
            }
        }finally {
            if( in!=null){
                in.close();
            }
            if(out !=null){
                out.close();
            }
        }
    }
}

class Student implements Comparable<Student> {
    private String maSV;
    private String hoTen;
    private String gioiTinh;
    private int diemPython;
    private int diemJava;
    private double diemTB;
    private String check;
    public Student(String maSV, String hoTen, String gioiTinh, int diemPython, int diemJava){
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.diemPython = diemPython;
        this.diemJava = diemJava;
        this.diemTB = (diemJava * 2 + diemPython) / 3;
    }

    public Student(String maSV, String hoTen, String gioiTinh, int diemPython, int diemJava, String check) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.diemPython = diemPython;
        this.diemJava = diemJava;
        this.diemTB = (diemJava * 2 + diemPython) / 3;
        this.check=check;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }
    public String getCheck() {
        return check;
    }

    public int getDiemPython() {
        return diemPython;
    }

    public int getDiemJava() {
        return diemJava;
    }

    public double getDiemTB() {
        return diemTB;
    }

    @Override
    public String toString() {
        return "Student [MSV=" +maSV+ ", name=" + hoTen + ", gender=" + gioiTinh + ", pypoint=" + diemPython + ", japoint="
                + diemJava + ", avg=" + diemTB+ ", check=" + check + "]";
    }

    public String CheckResult() {
        if (this.diemTB >= 5) {
            return "Đậu";
        } else if ("nam".equals(gioiTinh)) {
            return "Trượt";
        } else {
            return "Đậu";
        }

    }


    @Override
    public int compareTo(Student o) {
        return Double.compare(o.getDiemTB(), this.diemTB);
    }
}