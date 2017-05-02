package localsearch;

import util.ClassCreaterUtil;
import vrp.Solution;

/**
 * Created by ab792 on 2017/2/14.
 */
@FunctionalInterface
public interface BaseStretegy {
    /**
     * 路径的更新搜索
     *
     * @param preSolution
     * @throws Exception
     */
    public void updateSolution(Solution preSolution) throws Exception;

    /**
     * 局部搜索
     *
     * @param solution
     */
    default void improveSolution(Solution solution) throws Exception {
        doUpdate(_2OptStretegy::new, solution, 3);
        //System.out.println("2opt优化后-------------------------------->" + solution.calCost());
        doUpdate(_10RelocateStretegy::new, solution, 3);
        //System.out.println("10relocate优化后-------------------------------->" + solution.calCost());
        doUpdate(_2Opt$Stretegy::new, solution, 5);
        //System.out.println("2opt*优化后------------------------->" + solution.calCost());
        doUpdate(_10Relocate$Stretegy::new, solution, 5);
        //System.out.println("10Relocate$*优化后------------------------->" + solution.calCost());
    }


    default void doUpdate(ClassCreaterUtil classCreater, Solution solution, int times) throws Exception {
        for (int k = 0; k < times; k++) {
            classCreater.create().updateSolution(solution);
        }
    }

    ;
}