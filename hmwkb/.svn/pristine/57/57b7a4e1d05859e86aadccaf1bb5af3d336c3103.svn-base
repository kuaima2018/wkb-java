package com.chinadrtv.common.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * 
 * @author li
 * @version $Id: PaffMoney.java, v 0.1 2013-8-29 下午1:51:19 li Exp $
 * * 单币种货币类，处理货币算术、币种和取整。
 * 
 * <p>
 * 货币类中封装了货币金额和币种。目前金额在内部是long类型表示， 单位是所属币种的10的6次方。
 * 
 * <p>
 * 目前，货币实现了以下主要功能：<br>
 * <ul>
 * <li>支持货币对象与double(float)/long(int)/String/BigDecimal之间相互转换。
 * <li>货币类在运算中提供与JDK中的BigDecimal类似的运算接口， BigDecimal的运算接口支持任意指定精度的运算功能，能够支持各种
 * 可能的财务规则。
 * <li>货币类在运算中也提供一组简单运算接口，使用这组运算接口，则在 精度处理上使用缺省的处理规则。
 * <li>推荐使用Money，不建议直接使用BigDecimal的原因之一在于，
 * 使用BigDecimal，同样金额和币种的货币使用BigDecimal存在多种可能 的表示，例如：new BigDecimal("10.5")与new
 * BigDecimal("10.50") 不相等，因为scale不等。使得Money类，同样金额和币种的货币只有 一种表示方式，new
 * Money("10.5")和new Money("10.50")应该是相等的。
 * <li>不推荐直接使用BigDecimal的另一原因在于， BigDecimal是Immutable，
 * 一旦创建就不可更改，对BigDecimal进行任意运算都会生成一个新的 BigDecimal对象，因此对于大批量统计的性能不够满意。Money类是
 * mutable的，对大批量统计提供较好的支持。
 * <li>提供基本的格式化功能。
 * <li>Money类中不包含与业务相关的统计功能和格式化功能。业务相关的功能 建议使用utility类来实现。
 * <li>Money类实现了Serializable接口，支持作为远程调用的参数和返回值。
 * <li>Money类实现了equals和hashCode方法。
 * </ul>
 */

public class Money implements Serializable, Comparable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID      = 6009335074727417445L;

    /**
     * 缺省的币种代码，为CNY（人民币）。
     */
    public static final String DEFAULT_CURRENCY_CODE = "CNY";

    /**
     * 缺省的取整模式，为<code>BigDecimal.ROUND_HALF_EVEN
     * （四舍五入，当小数为0.5时，则取最近的偶数）。
     */
    public static final int    DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /**
     * 默认的扩展长度为6位
     */
    public static final int    DEFAULT_EXPAND_LENGTH = 6;
    /**
     * 以防decimal除不断，保留的小数位数
     */
    public static final int    DEFAULT_DIVIDE_LENGTH = 6;
    /**
     * 默认的放大倍数为1000000
     */
    public static final int    DEFAULT_EXPAND_VALUE  = 1000000;

    /**
     * 一组可能的元/分换算比例。
     * 
     * <p>
     * 此处，“分”是指货币的最小单位，“元”是货币的最常用单位， 不同的币种有不同的元/分换算比例，如人民币是100，而日元为1。
     */
    private static final int[] centFactors           = new int[] { 1, 10, 100, 1000 };

    private static final Long  max_amount_value      = Long.MAX_VALUE / DEFAULT_EXPAND_VALUE;

    private static final int   max_amount_length     = 13;

    private static final int   max_long_length       = String.valueOf(Long.MAX_VALUE).length();
    /**
     * 内部计算金额
     * <p>
     *   使用long来计算而不是BigDecimal,对BigDecimal进行任意运算都会生成一个新的 BigDecimal对象，因此对于大批量统计的性能不够满意
     *   为了计算精度更高，计算金额在实际金额的基础上放大了1000000倍。
     *   
     */
    private long               calculate_amount;

    private BigDecimal         calculate_big_amount;
    /**
     * 金额，以分为单位。
     */
    private long               cent;

    /**
     * 币种。
     */
    private Currency           currency;

    // 构造器 ====================================================

    /**
     * 缺省构造器。
     * 
     * <p>
     * 创建一个具有缺省金额（0）和缺省币种的货币对象。
     */
    public Money() {
        this(0);
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>yuan</code>元<code>cent</code>分和缺省币种的货币对象。
     * 
     * @param yuan
     *            金额元数。
     * @param cent
     *            金额分数。
     */
    public Money(long yuan, int cent) {
        this(yuan, cent, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>yuan</code>元<code>cent</code>分和指定币种的货币对象。
     * 
     * @param yuan
     *            金额元数。
     * @param cent
     *            金额分数。
     */

    public Money(long yuan, int cent, Currency currency) {
        this.currency = currency;
        if ((yuan + "").length() >= max_amount_length) {

            this.calculate_big_amount = new BigDecimal(yuan).add(BigDecimal.valueOf(cent,
                currency.getDefaultFractionDigits()));
        }
        this.cent = yuan * getCentFactor() + cent;
        this.calculate_amount = this.cent * DEFAULT_EXPAND_VALUE / getCentFactor();
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>amount</code>元和缺省币种的货币对象。
     * 
     * @param amount
     *            金额，以元为单位。
     */
    public Money(String amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
        //        if (!amount.matches("[0-9]*.[0-9][0-9]")) {
        //            throw new RuntimeException("金额格式不正确,应为XX.XX,实际值为：" + amount);
        //        }

    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>amount</code>元和指定币种<code>currency</code>的货币对象。
     * 
     * @param amount
     *            金额，以元为单位。
     * @param currency
     *            币种。
     */
    public Money(String amount, Currency currency) {
        this(new BigDecimal(amount), currency);
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>amount</code>元和指定币种<code>currency</code>的货币对象。
     * 如果金额不能转换为整数分，则使用指定的取整模式<code>roundingMode</code>取整。
     * 
     * @param amount
     *            金额，以元为单位。
     * @param currency
     *            币种。
     * @param roundingMode
     *            取整模式。
     */
    public Money(String amount, Currency currency, int roundingMode) {
        this(new BigDecimal(amount), currency, roundingMode);
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有参数<code>amount</code>指定金额和缺省币种的货币对象。 如果金额不能转换为整数分，则使用四舍五入方式取整。
     * 
     * <p>
     * 注意：由于double类型运算中存在误差，使用四舍五入方式取整的 结果并不确定，因此，应尽量避免使用double类型创建货币类型。 例：
     * 
     * @param amount
     *            金额，以元为单位。
     * 
     */
    public Money(double amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>amount</code>和指定币种的货币对象。 如果金额不能转换为整数分，则使用四舍五入方式取整。
     * @param amount
     *            金额，以元为单位。
     * @param currency
     *            币种。
     */
    public Money(double amount, Currency currency) {
        this.currency = currency;
        if (amount > max_amount_value) {
            calculate_big_amount = new BigDecimal(amount);
        } else {
            this.calculate_amount = Math.round(amount * DEFAULT_EXPAND_VALUE);
        }

    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>amount</code>和缺省币种的货币对象。 如果金额不能转换为整数分，则使用缺省取整模式
     * <code>DEFAULT_ROUNDING_MODE</code>取整。
     * 
     * @param amount
     *            金额，以元为单位。
     */
    public Money(BigDecimal amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有参数<code>amount</code>指定金额和缺省币种的货币对象。 如果金额不能转换为整数分，则使用指定的取整模式
     * <code>roundingMode</code>取整。
     * 
     * @param amount
     *            金额，以元为单位。
     * @param roundingMode
     *            取整模式
     * 
     */
    public Money(BigDecimal amount, int roundingMode) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE), roundingMode);
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>amount</code>和指定币种的货币对象。 如果金额不能转换为整数分，则使用缺省的取整模式
     * <code>DEFAULT_ROUNDING_MODE</code>进行取整。
     * 
     * @param amount
     *            金额，以元为单位。
     * @param currency
     *            币种
     */
    public Money(BigDecimal amount, Currency currency) {
        this(amount, currency, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 构造器。
     * 
     * <p>
     * 创建一个具有金额<code>amount</code>和指定币种的货币对象。 如果金额不能转换为整数分，则使用指定的取整模式
     * <code>roundingMode</code>取整。
     * 
     * @param amount
     *            金额，以元为单位。
     * @param currency
     *            币种。
     * @param roundingMode
     *            取整模式。
     */
    public Money(BigDecimal amount, Currency currency, int roundingMode) {
        this.currency = currency;
        assertOutOfBound(amount.toString());
        if (calculate_big_amount == null) {
            this.calculate_amount = rounding(amount.movePointRight(DEFAULT_EXPAND_LENGTH),
                roundingMode);
        }

    }

    /**
     * 获取本货币对象代表的金额数。
     * @return 金额数，以元为单位。
     */
    public BigDecimal getAmount() {
        if (calculate_big_amount != null) {
            return calculate_big_amount;
        }
        return BigDecimal.valueOf(calculate_amount, DEFAULT_EXPAND_LENGTH);
    }

    /**
     * 设置本货币对象代表的金额数。
     * 
     * @param amount
     *            金额数，以元为单位。
     */
    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            assertOutOfBound(amount.toString());
            if (this.calculate_big_amount == null) {
                calculate_amount = rounding(amount.movePointRight(DEFAULT_EXPAND_LENGTH),
                    BigDecimal.ROUND_HALF_EVEN);
            }

        }

    }

    /**
     * 获取本货币对象代表的金额数。
     * 如果金额超出了long的最大值，抛错
     * @return 金额数，以分为单位。
     * @throws Exception 
     */
    public long getCent() throws RuntimeException {
        if (calculate_big_amount != null) {
            throw new RuntimeException("超出long金额范围,请调用方法getAmount!");
        }
        return calculate_amount * getCentFactor() / DEFAULT_EXPAND_VALUE;
    }

    /**
     * 获取本货币对象代表的币种。
     * 
     * @return 本货币对象所代表的币种。
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * 获取本货币币种的元/分换算比率。
     * 
     * @return 本货币币种的元/分换算比率。
     */
    public int getCentFactor() {
        return centFactors[currency.getDefaultFractionDigits()];
    }

    // 基本对象方法 ===================================================

    /**
     * 判断本货币对象与另一对象是否相等。
     * 
     * <p>
     * 本货币对象与另一对象相等的充分必要条件是：<br>
     * <ul>
     * <li>另一对象也属货币对象类。
     * <li>金额相同。
     * <li>币种相同。
     * </ul>
     * 
     * @param other
     *            待比较的另一对象。
     * @return <code>true</code>表示相等，<code>false</code>表示不相等。
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        return (other instanceof Money) && equals((Money) other);
    }

    /**
     * 判断本货币对象与另一货币对象是否相等。
     * 
     * <p>
     * 本货币对象与另一货币对象相等的充分必要条件是：<br>
     * <ul>
     * <li>金额相同。
     * <li>币种相同。
     * </ul>
     * 
     * @param other
     *            待比较的另一货币对象。
     * @return <code>true</code>表示相等，<code>false</code>表示不相等。
     */
    public boolean equals(Money other) {
        //是不是少一种情况，如何当前是bigDecimal,other 是calculate_amount
        if (other.calculate_big_amount != null) {
            return currency.equals(other.currency)
                   && (getAmount().compareTo(other.calculate_big_amount) == 0);
        }
        return currency.equals(other.currency) && (calculate_amount == other.calculate_amount);
    }

    /**
     * 计算本货币对象的杂凑值。
     * 
     * @return 本货币对象的杂凑值。
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        if (this.calculate_big_amount != null) {
            //ymm 这里是否要加个决断
            return (int) (calculate_big_amount.hashCode() ^ (calculate_big_amount.hashCode() >>> 32));
        } else {
            //ymm 这里是否要加个决断
            return (int) (calculate_amount ^ (calculate_amount >>> 32));
        }

    }

    // Comparable接口 ========================================

    /**
     * 对象比较。
     * 
     * <p>
     * 比较本对象与另一对象的大小。 如果待比较的对象的类型不是<code>Money</code>，则抛出
     * <code>java.lang.ClassCastException</code>。 如果待比较的两个货币对象的币种不同，则抛出
     * <code>java.lang.IllegalArgumentException</code>。
     * 如果本货币对象的金额少于待比较货币对象，则返回-1。 如果本货币对象的金额等于待比较货币对象，则返回0。
     * 如果本货币对象的金额大于待比较货币对象，则返回1。
     * 
     * @param other
     *            另一对象。
     * @return -1表示小于，0表示等于，1表示大于。
     * 
     * @exception ClassCastException
     *                待比较货币对象不是<code>Money</code>。 IllegalArgumentException
     *                待比较货币对象与本货币对象的币种不同。
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object other) {
        return compareTo((Money) other);
    }

    /**
     * 货币比较。
     * 
     * <p>
     * 比较本货币对象与另一货币对象的大小。 如果待比较的两个货币对象的币种不同，则抛出
     * <code>java.lang.IllegalArgumentException</code>。
     * 如果本货币对象的金额少于待比较货币对象，则返回-1。 如果本货币对象的金额等于待比较货币对象，则返回0。
     * 如果本货币对象的金额大于待比较货币对象，则返回1。
     * 
     * @param other
     *            另一对象。
     * @return -1表示小于，0表示等于，1表示大于。
     * 
     * @exception IllegalArgumentException
     *                待比较货币对象与本货币对象的币种不同。
     */
    public int compareTo(Money other) {
        assertSameCurrencyAs(other);
        if (other.calculate_big_amount != null || this.calculate_big_amount != null) {
            return this.getAmount().compareTo(other.calculate_big_amount);
        }
        if (calculate_amount < other.calculate_amount) {
            return -1;
        } else if (calculate_amount == other.calculate_amount) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 货币比较。
     * 
     * <p>
     * 判断本货币对象是否大于另一货币对象。 如果待比较的两个货币对象的币种不同，则抛出
     * <code>java.lang.IllegalArgumentException</code>。
     * 如果本货币对象的金额大于待比较货币对象，则返回true，否则返回false。
     * 
     * @param other
     *            另一对象。
     * @return true表示大于，false表示不大于（小于等于）。
     * 
     * @exception IllegalArgumentException
     *                待比较货币对象与本货币对象的币种不同。
     */
    public boolean greaterThan(Money other) {
        return compareTo(other) > 0;
    }

    // 货币算术 ==========================================

    /**
     * 货币加法。
     * 
     * <p>
     * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为 两货币对象金额之和，本货币对象的值不变。 如果两货币对象币种不同，抛出
     * <code>java.lang.IllegalArgumentException</code>。
     * 
     * @param other
     *            作为加数的货币对象。
     * 
     * @exception IllegalArgumentException
     *                如果本货币对象与另一货币对象币种不同。
     * 
     * @return 相加后的结果。
     */
    public Money add(Money other) {
        if (other.calculate_big_amount != null
            || this.calculate_big_amount != null
            || Math.max((this.calculate_amount + "").length(),
                (other.calculate_amount + "").length()) + 1 >= max_long_length) {
            return newMoneyWithSameCurrency(this.getAmount().add(other.getAmount()));
        }
        return newMoneyWithSameCurrency(calculate_amount + other.calculate_amount);
    }

    /**
     * 货币累加。
     * 
     * <p>
     * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之和，并返回本货币对象的引用。 如果两货币对象币种不同，抛出
     * <code>java.lang.IllegalArgumentException</code>。
     * 
     * @param other
     *            作为加数的货币对象。
     * 
     * @exception IllegalArgumentException
     *                如果本货币对象与另一货币对象币种不同。
     * 
     * @return 累加后的本货币对象。
     */
    public Money addTo(Money other) {
        assertSameCurrencyAs(other);
        if (other.calculate_big_amount != null
            || this.calculate_big_amount != null
            || Math.max((this.calculate_amount + "").length(),
                (other.calculate_amount + "").length()) + 1 >= max_long_length) {
            this.calculate_big_amount = getAmount().add(other.getAmount());
            return this;
        }
        this.calculate_amount += other.calculate_amount;

        return this;
    }

    /**
     * 货币减法。
     * 
     * <p>
     * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为 本货币对象的金额减去参数货币对象的金额。本货币对象的值不变。
     * 如果两货币币种不同，抛出<code>java.lang.IllegalArgumentException</code>。
     * 
     * @param other
     *            作为减数的货币对象。
     * 
     * @exception IllegalArgumentException
     *                如果本货币对象与另一货币对象币种不同。
     * 
     * @return 相减后的结果。
     */
    public Money subtract(Money other) {
        assertSameCurrencyAs(other);
        if (other.calculate_big_amount != null || this.calculate_big_amount != null) {
            return newMoneyWithSameCurrency(this.getAmount().subtract(other.getAmount()));
        }
        return newMoneyWithSameCurrency(calculate_amount - other.calculate_amount);
    }

    /**
     * 货币累减。
     * 
     * <p>
     * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之差，并返回本货币对象的引用。 如果两货币币种不同，抛出
     * <code>java.lang.IllegalArgumentException</code>。
     * 
     * @param other
     *            作为减数的货币对象。
     * 
     * @exception IllegalArgumentException
     *                如果本货币对象与另一货币对象币种不同。
     * 
     * @return 累减后的本货币对象。
     */
    public Money subtractFrom(Money other) {
        assertSameCurrencyAs(other);
        if (other.calculate_big_amount != null || this.calculate_big_amount != null) {
            this.calculate_big_amount = getAmount().subtract(other.getAmount());
            return this;
        }
        this.calculate_amount -= other.calculate_amount;

        return this;
    }

    /**
     * 货币乘法。
     * 
     * <p>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。 本货币对象的值不变。
     * 
     * @param val
     *            乘数
     * 
     * @return 乘法后的结果。
     */
    public Money multiply(long val) {
        if (this.calculate_big_amount != null
            || getAddLength(calculate_amount, val) >= max_long_length) {
            return newMoneyWithSameCurrency(getAmount().multiply(new BigDecimal(val)));
        }
        return newMoneyWithSameCurrency(calculate_amount * val);
    }

    /**
     * 货币累乘。
     * 
     * <p>
     * 本货币对象金额乘以乘数，并返回本货币对象。
     * 
     * @param val
     *            乘数
     * 
     * @return 累乘后的本货币对象。
     */
    public Money multiplyBy(long val) {
        if (this.calculate_big_amount != null
            || getAddLength(calculate_amount, val) >= max_long_length) {
            this.calculate_big_amount = getAmount().multiply(new BigDecimal(val));
            return this;
        }
        this.calculate_amount *= val;

        return this;
    }

    /**
     * 货币乘法。
     * 
     * <p>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。 本货币对象的值不变。如果相乘后的金额不能转换为整数分，则四舍五入。
     * 
     * @param val
     *            乘数
     * 
     * @return 相乘后的结果。
     */
    public Money multiply(double val) {
        if (this.calculate_big_amount != null
            || getAddLength(calculate_amount, val) >= max_long_length) {
            return newMoneyWithSameCurrency(getAmount().multiply(new BigDecimal(val)));
        }
        return newMoneyWithSameCurrency(Math.round(calculate_amount * val));
    }

    /**
     * 货币累乘。
     * 
     * <p>
     * 本货币对象金额乘以乘数，并返回本货币对象。 如果相乘后的金额不能转换为整数分，则使用四舍五入。
     * 
     * @param val
     *            乘数
     * 
     * @return 累乘后的本货币对象。
     */
    public Money multiplyBy(double val) {
        if (this.calculate_big_amount != null
            || getAddLength(calculate_amount, val) >= max_long_length) {
            this.calculate_big_amount = getAmount().multiply(new BigDecimal(val));
            return this;
        }

        this.calculate_amount = Math.round(this.calculate_amount * val);

        return this;
    }

    /**
     * 货币乘法。
     * 
     * <p>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用缺省的取整模式 <code>DEFUALT_ROUNDING_MODE</code>
     * 进行取整。
     * 
     * @param val
     *            乘数
     * 
     * @return 相乘后的结果。
     */
    public Money multiply(BigDecimal val) {
        return multiply(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币累乘。
     * 
     * <p>
     * 本货币对象金额乘以乘数，并返回本货币对象。 如果相乘后的金额不能转换为整数分，使用缺省的取整方式
     * <code>DEFUALT_ROUNDING_MODE</code>进行取整。
     * 
     * @param val
     *            乘数
     * 
     * @return 累乘后的结果。
     */
    public Money multiplyBy(BigDecimal val) {
        return multiplyBy(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币乘法。
     * 
     * <p>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用指定的取整方式 <code>roundingMode</code>进行取整。
     * 
     * @param val
     *            乘数
     * @param roundingMode
     *            取整方式
     * 
     * @return 相乘后的结果。
     */
    public Money multiply(BigDecimal val, int roundingMode) {
        BigDecimal newcalculate_amount = getAmount().multiply(val);
        if (isOutOfBound(newcalculate_amount.toString())) {
            return newMoneyWithSameCurrency(newcalculate_amount);
        }
        return newMoneyWithSameCurrency(rounding(
            newcalculate_amount.movePointRight(DEFAULT_EXPAND_LENGTH), roundingMode));
    }

    /**
     * 货币累乘。
     * 
     * <p>
     * 本货币对象金额乘以乘数，并返回本货币对象。 如果相乘后的金额不能转换为整数分，使用指定的取整方式
     * <code>roundingMode</code>进行取整。
     * 
     * @param val
     *            乘数
     * @param roundingMode
     *            取整方式
     * 
     * @return 累乘后的结果。
     */
    public Money multiplyBy(BigDecimal val, int roundingMode) {
        BigDecimal newcalculate_amount = getAmount().multiply(val);
        assertOutOfBound(newcalculate_amount.toString());
        if (this.calculate_big_amount == null) {
            this.calculate_amount = rounding(
                newcalculate_amount.movePointRight(DEFAULT_EXPAND_LENGTH), roundingMode);
        }

        return this;
    }

    /**
     * 货币除法。
     * 
     * <p>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
     * 
     * @param val
     *            除数,应为大于1的数
     * 
     * @return 相除后的结果。
     */
    public Money divide(double val) {

        if (this.calculate_big_amount != null || calculate_amount / val >= Long.MAX_VALUE) {

            return newMoneyWithSameCurrency(getAmount().divide(new BigDecimal(val),
                DEFAULT_DIVIDE_LENGTH, DEFAULT_ROUNDING_MODE));
        }
        return newMoneyWithSameCurrency(Math.round(calculate_amount / val));
    }

    /**
     * 货币累除。
     * 
     * <p>
     * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
     * 
     * @param val
     *            除数
     * 
     * @return 累除后的结果。
     */
    public Money divideBy(double val) {
        if (this.calculate_big_amount != null || calculate_amount / val > max_amount_value) {
            this.calculate_big_amount = getAmount().divide(new BigDecimal(val),
                DEFAULT_DIVIDE_LENGTH, DEFAULT_ROUNDING_MODE);
            return this;
        }
        this.calculate_amount = Math.round(this.calculate_amount / val);

        return this;
    }

    /**
     * 货币除法。
     * 
     * <p>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用缺省的取整模式 <code>DEFAULT_ROUNDING_MODE</code>
     * 进行取整。
     * 
     * @param val
     *            除数
     * 
     * @return 相除后的结果。
     */
    public Money divide(BigDecimal val) {
        return divide(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币除法。
     * 
     * <p>
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用指定的取整模式 <code>roundingMode</code>进行取整。
     * 
     * @param val
     *            除数
     * @param roundingMode
     *            取整
     * 
     * @return 相除后的结果。
     */
    public Money divide(BigDecimal val, int roundingMode) {
        BigDecimal newcalculate_amount = getAmount().divide(val, DEFAULT_DIVIDE_LENGTH,
            roundingMode);

        if (isOutOfBound(newcalculate_amount.toString())) {
            return newMoneyWithSameCurrency(newcalculate_amount);
        }
        return newMoneyWithSameCurrency(rounding(
            newcalculate_amount.movePointRight(DEFAULT_EXPAND_LENGTH), roundingMode));
    }

    /**
     * 货币累除。
     * 
     * <p>
     * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用缺省的取整模式
     * <code>DEFAULT_ROUNDING_MODE</code>进行取整。
     * 
     * @param val
     *            除数
     * 
     * @return 累除后的结果。
     */
    public Money divideBy(BigDecimal val) {
        return divideBy(val, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 货币累除。
     * 
     * <p>
     * 本货币对象金额除以除数，并返回本货币对象。 如果相除后的金额不能转换为整数分，使用指定的取整模式
     * <code>roundingMode</code>进行取整。
     * 
     * @param val
     *            除数
     * 
     * @return 累除后的结果。
     */
    public Money divideBy(BigDecimal val, int roundingMode) {
        BigDecimal newcalculate_amount = getAmount().divide(val, DEFAULT_DIVIDE_LENGTH,
            roundingMode);
        if (isOutOfBound(newcalculate_amount.toString())) {
            this.calculate_big_amount = newcalculate_amount;
            return this;
        }
        this.calculate_amount = rounding(newcalculate_amount.movePointRight(DEFAULT_EXPAND_LENGTH),
            roundingMode);

        return this;
    }

    /**
     * 货币分配。
     * 
     * <p>
     * 将本货币对象尽可能平均分配成<code>targets</code>份。 如果不能平均分配尽，则将零头放到开始的若干份中。分配
     * 运算能够确保不会丢失金额零头。
     * 
     * @param targets
     *            待分配的份数
     * 
     * @return 货币对象数组，数组的长度与分配份数相同，数组元素 从大到小排列，所有货币对象的金额最多只相差1分。
     */
    public Money[] allocate(int targets) {

        Money[] results = new Money[targets];
        if (this.calculate_big_amount != null) {
            Money result = newMoneyWithSameCurrency(calculate_big_amount.divide(new BigDecimal(
                targets), DEFAULT_DIVIDE_LENGTH, DEFAULT_ROUNDING_MODE));
            for (int i = 0; i < targets; i++) {
                results[i] = result;
            }
            return results;
        }
        Money lowResult = newMoneyWithSameCurrency(calculate_amount / targets);
        Money highResult = newMoneyWithSameCurrency(lowResult.calculate_amount + 1);

        int remainder = (int) calculate_amount % targets;

        for (int i = 0; i < remainder; i++) {
            results[i] = highResult;
        }

        for (int i = remainder; i < targets; i++) {
            results[i] = lowResult;
        }

        return results;
    }

    /**
     * 货币分配。
     * 
     * <p>
     * 将本货币对象按照规定的比例分配成若干份。分配所剩的零头 从第一份开始顺序分配。分配运算确保不会丢失金额零头。
     * 
     * @param ratios
     *            分配比例数组，每一个比例是一个长整型，代表 相对于总数的相对数。
     * 
     * @return 货币对象数组，数组的长度与分配比例数组的长度相同。
     */
    public Money[] allocate(long[] ratios) {
        Money[] results = new Money[ratios.length];

        long total = 0;

        for (int i = 0; i < ratios.length; i++) {
            total += ratios[i];
        }

        long remainder = calculate_amount;

        for (int i = 0; i < results.length; i++) {
            results[i] = newMoneyWithSameCurrency((calculate_amount * ratios[i]) / total);
            remainder -= results[i].calculate_amount;
        }

        for (int i = 0; i < remainder; i++) {
            results[i].calculate_amount++;
        }

        return results;
    }

    // 格式化方法 =================================================

    /**
     * 生成本对象的缺省字符串表示
     */
    public String toString() {
        return getAmount().toString();
    }

    // 内部方法 ===================================================

    /**
     * 断言本货币对象与另一货币对象是否具有相同的币种。
     * 
     * <p>
     * 如果本货币对象与另一货币对象具有相同的币种，则方法返回。 否则抛出运行时异常
     * <code>java.lang.IllegalArgumentException</code>。
     * 
     * @param other
     *            另一货币对象
     * 
     * @exception IllegalArgumentException
     *                如果本货币对象与另一货币对象币种不同。
     */
    protected void assertSameCurrencyAs(Money other) {
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("Money math currency mismatch.");
        }
    }

    /**
     * 对BigDecimal型的值按指定取整方式取整。
     * 
     * @param val
     *            待取整的BigDecimal值
     * @param roundingMode
     *            取整方式
     * 
     * @return 取整后的long型值
     */
    protected long rounding(BigDecimal val, int roundingMode) {
        return val.setScale(0, roundingMode).longValue();
    }

    /**
     * 若long的长度逼近越界或者越界，则内部计算使用BigDecimal.
     * 
     * @param value
     */
    private void assertOutOfBound(String value) {
        if (isOutOfBound(value)) {
            this.calculate_big_amount = new BigDecimal(value);
        }
    }

    private Boolean isOutOfBound(String value) {
        int length = 0;
        if (value.contains(".")) {
            length = value.split("\\.")[0].length();
        } else {
            length = value.length();
        }
        if (length >= max_amount_length) {
            return true;
        }
        return false;
    }

    /**
     * 两个对象的长度的和
     * 
     * @param x1
     * @param x2
     * @return
     */
    private int getAddLength(Object x1, Object x2) {
        int length = 0;
        String value = x2.toString();
        if (value.contains(".")) {
            length = value.split("\\.")[0].length();
        } else {
            length = value.length();
        }
        return x1.toString().length() + length;
    }

    /**
     * 创建一个币种相同，具有指定金额的货币对象。
     * 
     * @param calculate_amount
     *            金额，以元为单位的基础上扩大10的6次方
     * 
     * @return 一个新建的币种相同，具有指定金额的货币对象
     */
    protected Money newMoneyWithSameCurrency(long calculate_amount) {
        Money money = new Money(0, currency);

        money.calculate_amount = calculate_amount;

        return money;
    }

    protected Money newMoneyWithSameCurrency(BigDecimal calculate_big_amount) {
        Money money = new Money(0, currency);

        money.calculate_big_amount = calculate_big_amount;

        return money;
    }

    // 调试方式 ==================================================

    /**
     * 生成本对象内部变量的字符串表示，用于调试。
     * 
     * @return 本对象内部变量的字符串表示。
     */
    public String dump() {
        String lineSeparator = System.getProperty("line.separator");

        StringBuffer sb = new StringBuffer();

        sb.append("amount = ").append(getAmount()).append(lineSeparator);
        sb.append("currency = ").append(currency);

        return sb.toString();
    }

    /**
     * 
     * 
     * @return property value of currencyCode
     */
    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

    /**
     * Setter method for property <tt>currencyCode</tt>.
     * 
     * @param currencyCode value to be assigned to property currencyCode
     */
    public void setCurrencyCode(String currencyCode) {
        this.currency = Currency.getInstance(currencyCode);
    }

    /**
     * 参数应为实际金额的10的6次方;
     * 
     * @param yuan value to be assigned to property yuan
     */
    public void setYuan(BigDecimal yuan) {
        if (yuan != null) {
            assertOutOfBound(yuan.movePointLeft(DEFAULT_EXPAND_LENGTH).toString());
            if (this.calculate_big_amount == null) {
                calculate_amount = rounding(yuan, BigDecimal.ROUND_HALF_EVEN);
            }

        }
    }

    /**
     * 获取一个在实际金额上扩大10的6次方的BigDecimal;
     * 可以持久化到一个NUMBER字段
     * @return  
     */
    public BigDecimal getYuan() {
        if (calculate_big_amount != null) {
            return getAmount().movePointRight(6);
        }
        return new BigDecimal(this.calculate_amount);
    }

    /**
     * 获取一个在实际金额上扩大10的6次方的long;
     * 金额数与数据库中的金额相等；
     * 当实际金额大于万亿抛错.
     * @return
     */
    public long getLongYuan() {
        if (calculate_big_amount != null) {
            throw new RuntimeException("超出Long的最大值");
        }
        return this.calculate_amount;
    }

    /**
     * 获取一个在实际金额上扩大10的6次方的BigDecimal;
     * 金额数与数据库中的金额相等；
     * @return
     */
    public BigDecimal getDecimalYuan() {
        if (calculate_big_amount != null) {
            return calculate_big_amount.movePointRight(6).setScale(0, DEFAULT_ROUNDING_MODE);
        }
        return new BigDecimal(this.calculate_amount);
    }

    /**
     * 获取一个在实际金额上扩大10的6次方的BigDecimal的负数;
     * 金额数与数据库中的金额相等;
     * @return  
     */
    public BigDecimal getNegeteYuan() {
        if (calculate_big_amount != null) {
            return getAmount().movePointRight(6).negate();
        }
        return new BigDecimal(this.calculate_amount).negate();
    }

}
