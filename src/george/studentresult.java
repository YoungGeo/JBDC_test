package george;
/* 
 * author: George Young
 * Date Created: September 16th 2016
 * Last Modified: September 30th 2016
 *
 */
import java.util.Objects;

public class studentresult {
    //Made to be used to contain all students in a certain course as a arraylist

    String id;//Holds student ID

    //2 constructors one with args
    public studentresult() {

    }

    public studentresult(String sid) {
        id = sid;
    }

    //getter and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    //HASHCODE and equals
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final studentresult other = (studentresult) obj;
        return Objects.equals(this.id, other.id);
    }

    

    
    
    

}
