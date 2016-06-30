package wiener2D;

import java.util.*;

/**
 * Title:        DeconvolutionJ --- Image Reconstruction with ImageJ
 * Description:
 * Copyright:    Copyright (c) 2001, 2002
 * Company:      Wake Forest University School of Medicine, Department of Medical Engineering
 *               Winston-Salem, North Carolina, USA
 * @author       Nick Linnenbr�gger (nilin@web.de)
 *
 * See http://w3.one.net/~jweirich/java/patterns/observer/javaobserver.html for more information.
 */

public class PublicObservable2 extends Observable
{
    public void setChanged() { super.setChanged(); }
}